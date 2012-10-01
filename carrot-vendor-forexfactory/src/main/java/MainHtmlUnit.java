import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

public class MainHtmlUnit {

	static final Logger logger = LoggerFactory.getLogger(MainHtmlUnit.class);

	public static void main(String[] args) throws Exception {

		logger.debug("init");

		test1();

	}

	static void test1() throws Exception {

		//

		final String home = System.getProperty("user.home");
		final File file = new File(home, "Temp/account.properties");
		final FileInputStream input = new FileInputStream(file);

		final Properties props = new Properties();
		props.load(input);

		//

		final WebClient client = new WebClient(BrowserVersion.FIREFOX_3_6);

		final HtmlPage page1 = client.getPage(props.getProperty("location"));

		logger.debug("title = " + page1.getTitleText());

		// final String pageAsXml = page.asXml();
		// logger.debug("pageAsXml = \n" + pageAsXml);

		logger.debug("page \n" + page1.asText());

		final HtmlForm form = page1.getFormByName("loginform");

		final HtmlInput username = form.getInputByName("username");
		final HtmlInput password = form.getInputByName("password");
		final HtmlInput submit = form.getInputByValue("Submit");

		username.setValueAttribute(props.getProperty("username"));
		password.setValueAttribute(props.getProperty("password"));

		final Page page2 = submit.click();

		logger.debug("page \n" + page2.toString());

		final HtmlPage page3 = client.getPage(page2.getUrl());

		logger.debug("page \n" + page3.asText());
		logger.debug("page \n" + page3.asXml());

		final HtmlTable table = page3.getHtmlElementById("account-list");

		for (final HtmlTableRow row : table.getRows()) {
			System.out.println("Found row");
			for (final HtmlTableCell cell : row.getCells()) {
				System.out.println("   Found cell: " + cell.asText());
			}
		}

		client.closeAllWindows();

	}

}
