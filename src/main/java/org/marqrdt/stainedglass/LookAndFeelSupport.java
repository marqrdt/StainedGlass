package org.marqrdt.stainedglass;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/*
 * Installs the Nimbus look and feel (built into the JDK) in place of the
 * default Metal look and feel, giving the game a more modern appearance
 * without adding any new dependencies. If Nimbus is not available for some
 * reason, the platform default look and feel is left in place.
 */
public abstract class LookAndFeelSupport {

	public static void installNimbus() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					return;
				}
			}
		} catch (Exception e) {
			// Nimbus could not be installed; fall back to the default look and feel.
		}
	}
}
