package com.carrotgarden.vendor.oanda;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.typesafe.config.Config;

/**
 * 
 */
public class UtilWeb {

	private static final ThreadLocal<WebClient> CLIENT = new ThreadLocal<WebClient>() {
		@Override
		protected WebClient initialValue() {
			try {

				log.info("client for thread: {}", Thread.currentThread()
						.getName());

				final Config reference = Util.reference();

				final String username = reference
						.getString("oanda.login.username");
				final String password = reference
						.getString("oanda.login.password");

				final String urlLogin = reference.getString("oanda.page.login");

				final WebClient client = new WebClient(
						BrowserVersion.FIREFOX_24);
				client.getOptions().setJavaScriptEnabled(false);

				final HtmlPage login = client.getPage(urlLogin);

				log.info("login: " + login);

				final HtmlForm form = login
						.getFirstByXPath("//form[@id='loginform']");

				log.info("form: " + form);

				final HtmlTextInput textUser = form
						.getFirstByXPath("//input[@id='username']");
				final HtmlPasswordInput textPass = form
						.getFirstByXPath("//input[@id='password']");
				final HtmlCheckBoxInput checkStart = form
						.getFirstByXPath("//input[@id='start_trading']");
				final HtmlSubmitInput button = form
						.getFirstByXPath("//input[@id='submit']");

				textUser.setValueAttribute(username);
				textPass.setValueAttribute(password);
				checkStart.setChecked(false);
				final HtmlPage account = button.click();

				log.info("account: " + account);

				return client;
			} catch (final Throwable e) {
				log.error("CLIENT failure", e);
				return null;
			}
		}
	};

	private static final Logger log = LoggerFactory.getLogger(UtilWeb.class);

	/**
	 * New authenticated HTML client.
	 */
	public static WebClient clientHtml() throws IOException {
		return CLIENT.get();
	}

	/**
	 * Configure observer call back.
	 */
	public static void ensureWebhook(final WebClient client, final int projectId)
			throws IOException {

		final Config reference = Util.reference();

		final String webhook = reference.getString("pivotal.webhook");
		final String urlIntergate = reference.getString(
				"pivotal.page.integrate").replace("(project_id)",
				Integer.toString(projectId));

		final HtmlPage integrate = client.getPage(urlIntergate);

		log.info("integrate: " + integrate);

		final HtmlForm form = integrate
				.getFirstByXPath("//div[@class='webhooks']//form");

		log.info("form: " + form);

		final HtmlTextInput textURL = form
				.getFirstByXPath("//input[@id='activity_channel_webhook_url']");
		final HtmlSubmitInput button = form
				.getFirstByXPath("//input[@id='save_webhook_settings']");

		textURL.setValueAttribute(webhook);

		button.click();

	}

	/**
	 * Configure pivotal -> github integration.
	 */
	public static void ensureIntegration(final WebClient client,
			final int projectId) throws IOException {

		final Config reference = Util.reference();

		final String integrationName = reference
				.getString("pivotal.integration.name");
		final String integrationBaseURL = reference
				.getString("pivotal.integration.base-url");
		final String integrationImportURL = reference
				.getString("pivotal.integration.import-url");

		final String urlIntergate = reference.getString(
				"pivotal.page.integrate").replace("(project_id)",
				Integer.toString(projectId));

		{

			// https://www.pivotaltracker.com/projects/896678/integrations/new?type=Other

			final String urlIntergateNew = urlIntergate + "/new?type=Other";

			final HtmlPage integrateNew = client.getPage(urlIntergateNew);

			final HtmlForm form = integrateNew
					.getFirstByXPath("//div[@class='integration']//form");

			log.info("form={}", form);

			final HtmlTextInput textName = form
					.getFirstByXPath("//input[@id='integration_name']");
			final HtmlTextInput textBaseURL = form
					.getFirstByXPath("//input[@id='integration_base_url']");
			final HtmlTextInput textImportURL = form
					.getFirstByXPath("//input[@id='integration_import_api_url']");
			final HtmlSubmitInput button = form
					.getFirstByXPath("//input[@id='create_button']");

			textName.setValueAttribute(integrationName);
			textBaseURL.setValueAttribute(integrationBaseURL);
			textImportURL.setValueAttribute(integrationImportURL);

			button.click();

		}

		{

			final HtmlPage integrate = client.getPage(urlIntergate);

			log.info("integrate: " + integrate);

			final HtmlTable table = integrate
					.getFirstByXPath("//table[@id='integrations_table']");

			log.info("table: " + table);

			final List<HtmlTableRow> rowList = table.getRows();

			Integer integrationId = null;

			for (final HtmlTableRow row : rowList) {
				final String id = row.getId();
				if (id == null || id.length() == 0) {
					continue;
				}
				final String name = row.getCell(2).getTextContent().trim();

				if (name.contains(integrationName)) {
					integrationId = Integer.parseInt(id.split("_")[1]);
				}
			}

		}

	}

}
