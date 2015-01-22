package SCProject.eHealth;

import java.util.ResourceBundle;

import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class EHealthApplication extends ResourceConfig {
	public EHealthApplication() {
		super();

		register(DeclarativeLinkingFeature.class);
		//ResourceBundle bundle = ResourceBundle.getBundle("application");

	}
}
