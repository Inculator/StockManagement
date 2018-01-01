package com.mg.authentication;

import com.mg.filesystem.directory.DirectoryUtils;

public interface CheckAuthenticDrive extends DirectoryUtils {

	boolean authenticationCheck();
}
