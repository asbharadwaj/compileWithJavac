package compilewithjavac.launching;

import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;

import org.eclipse.jdt.debug.ui.launchConfigurations.JavaMainTab;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;

public class JavacArgumentsTab extends JavaMainTab {

//	@Override
//	public String getName() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy config) {
		IJavaElement javaElement = getContext();
		if (javaElement != null) {
			initializeJavaProject(javaElement, config);
		}
		else {
			config.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");
		}
		initializeFileName(javaElement, config);
		
	}
	protected void initializeFileName(IJavaElement javaElement, ILaunchConfigurationWorkingCopy config) {
		String name = "";
		ICompilationUnit cu = null;
		if (javaElement instanceof IMember) {
			IMember member = (IMember)javaElement;
			if (member.isBinary()) {
				//javaElement = member.getClassFile();
			}
			else {
				cu = member.getCompilationUnit();
			}
		} else if (javaElement instanceof ICompilationUnit) {
			cu = (ICompilationUnit) javaElement;
		}
		if (cu != null) {
			name = cu.getResource().getProjectRelativePath().toString();
			if (name.length() > 0) {
				String configName = getLaunchConfigurationDialog().generateName(cu.getElementName());
				config.rename(configName);
			}
		}
		config.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, name);
	}
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		// TODO Auto-generated method stub
		super.performApply(configuration);
	}

}
