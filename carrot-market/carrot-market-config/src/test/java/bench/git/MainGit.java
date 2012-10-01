package bench.git;

import java.io.File;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainGit {

	static final Logger logger = LoggerFactory.getLogger(MainGit.class);

	private static final String REPO = "./target/repo";
	private static final File FILE = new File(REPO);

	private static final String USER = "";
	private static final String PASS = "";

	private static final String URI = "git@github.com:carrot-garden/carrot-market.git";

	public static void main(final String[] args) throws Exception {

		logger.debug("init");

		final FileRepositoryBuilder builder = new FileRepositoryBuilder();

		final Repository repo = builder.setGitDir(FILE).readEnvironment()
				.findGitDir().build();

		// final Git git = Git.open(FILE);
		// final StatusCommand status = git.status();
		// final Status result = status.call();
		// logger.debug("result=" + result);

		testClone();

		final Git git = new Git(repo);

		// final RevCommit commit =
		// git.commit().setMessage("test commit").call();

		// final RevTag tag = git.tag().setName("test_tag").call();

		logger.debug("done");

	}

	static void testClone() {

		final CloneCommand clone = Git.cloneRepository();

		clone.setBare(false);

		clone.setBranch("master");

		clone.setCloneAllBranches(true);

		clone.setDirectory(FILE).setURI(URI);

		final CredentialsProvider provider = //
		new UsernamePasswordCredentialsProvider(USER, PASS);

		clone.setCredentialsProvider(provider);

		clone.call();

	}

}
