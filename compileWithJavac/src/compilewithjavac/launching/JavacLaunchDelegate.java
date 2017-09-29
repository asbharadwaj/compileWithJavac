package compilewithjavac.launching;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate2;
import org.eclipse.jdt.internal.launching.LaunchingMessages;
import org.eclipse.jdt.launching.AbstractJavaLaunchConfigurationDelegate;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.osgi.util.NLS;

public class JavacLaunchDelegate extends AbstractJavaLaunchConfigurationDelegate {

	private static final String[] JAVAC_EXECUTABLE_NAMES = {"javac", "javac.exe"};
	private static final String[] LOCATIONS = {"bin" + File.separator};
	
	File findJavacExecutable(IVMInstall install) {
		File location = install.getInstallLocation();
		for (int i = 0; i < JAVAC_EXECUTABLE_NAMES.length; i++) {
			for (int j = 0; j < LOCATIONS.length; j++) {
				File javaFile = new File(location, LOCATIONS[j] + JAVAC_EXECUTABLE_NAMES[i]);
				if (javaFile.isFile()) {
					return javaFile;
				}
			}
		}
		return null;
	}
	public IVMInstall verifyVMInstall(ILaunchConfiguration configuration)
			throws CoreException {
		IVMInstall install = super.verifyVMInstall(configuration);
		File javacExecutable = findJavacExecutable(install);
		if (javacExecutable == null) {
			abort(
					"Cannot be used with JRE, requires JDK",
					null,
					IJavaLaunchConfigurationConstants.ERR_VM_INSTALL_DOES_NOT_EXIST);
		}
		return install;
	}
	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}

		monitor.beginTask(NLS.bind("{0}...", new String[]{configuration.getName()}), 3); //$NON-NLS-1$
		// check for cancellation
		if (monitor.isCanceled()) {
			return;
		}

	}
//
//	@Override
//	public ILaunch getLaunch(ILaunchConfiguration configuration, String mode) throws CoreException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean buildForLaunch(ILaunchConfiguration configuration, String mode, IProgressMonitor monitor)
//			throws CoreException {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean finalLaunchCheck(ILaunchConfiguration configuration, String mode, IProgressMonitor monitor)
//			throws CoreException {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean preLaunchCheck(ILaunchConfiguration configuration, String mode, IProgressMonitor monitor)
//			throws CoreException {
//		// TODO Auto-generated method stub
//		return false;
//	}

}
