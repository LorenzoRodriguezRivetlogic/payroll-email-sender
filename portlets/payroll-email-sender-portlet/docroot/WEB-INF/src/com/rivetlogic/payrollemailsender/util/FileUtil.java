package com.rivetlogic.payrollemailsender.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.opencsv.CSVReader;
import com.rivetlogic.payrollemailsender.model.FileColumn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FileUtil {
	
	private static final Log LOG = LogFactoryUtil.getLog(FileUtil.class);
	
	public static final String DEFAULT_FILE_NAME = "CSV - ";
    public static final String DEFAULT_FOLDER_NAME = "Payrolls";
    public static final String DEFAULT_FOLFER_DESC = "Uploaded Payrolls";
    
	private static boolean isAttachExistsInRequest(final UploadPortletRequest req) {
        final FileItem[] arr = req.getMultipartParameterMap().get("attachedFile");
        if (arr == null || arr.length == 0) {
            return false;
        }
        boolean isAttachExists = false;
        for (final FileItem fi : arr) {
            if (!fi.getFileName().isEmpty()) {
                isAttachExists = true;
            }
        }
        return isAttachExists;
    }
	
	public static long storeAttachments(final UploadPortletRequest req) 
	    throws PrincipalException, Exception {
		long fileId = 0l;
		
	    if (isAttachExistsInRequest(req)) {
	        final FileItem[] arr = req.getMultipartParameterMap().get("attachedFile");
	        final ServiceContext sc = new ServiceContext();
	        final long repoId = getRepositoryId(req);
	        final long folderId = getFolderId(repoId, sc);
	        for (final FileItem fi : arr) {
	            fileId = createFileEntry(repoId, folderId, fi.getFileName(),fi.getContentType(), fi.getSize(), fi.getInputStream(), sc);
	        }
	    }
	    
	    return fileId;
	}

	private static long getRepositoryId(final UploadPortletRequest req) {
	    final ThemeDisplay themeDisplay = (ThemeDisplay) req.getAttribute(WebKeys.THEME_DISPLAY);
	    return themeDisplay.getScopeGroupId();
	}

	private static long createFileEntry(final long repositoryId,
	        final long folderId, final String fileName, final String mimeType,
	        final long fileLength, final InputStream is,
	        final ServiceContext serviceContext) {
		
		long id = 0l;
		
	    try {
	    	
	    	String name = String.format("%s - %s - %s", DEFAULT_FILE_NAME, Calendar.getInstance().getTimeInMillis(), fileName);
	        final FileEntry entry = DLAppServiceUtil.addFileEntry(repositoryId,
	                folderId, fileName, mimeType, name, null, null, is,
	                fileLength, serviceContext);
	        
	        id = entry.getFileEntryId();
	    } catch (final Exception e) {
	        LOG.error("Utils::createFileEntry Exception", e);
	    }
	    
	    return id;
	}
	
	public static FileEntry getFileEntry(final long fileId) {
		FileEntry fileEntry = null;
		
		try {
			fileEntry = DLAppServiceUtil.getFileEntry(fileId);
		} catch (PortalException e) {
			LOG.error("Utils::getFileEntry Exception", e);
		} catch (SystemException e) {
			LOG.error("Utils::getFileEntry Exception", e);
		}
		
		return fileEntry;
	}
	
	private static long getFolderId(final long repositoryId, final ServiceContext serviceContext) throws PortalException, SystemException {
		Folder folder = null;
		long parent = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
        try {
            folder = DLAppServiceUtil.getFolder(repositoryId, parent, DEFAULT_FOLDER_NAME);
        } catch(Exception e) {
            folder = DLAppServiceUtil.addFolder(repositoryId, parent, DEFAULT_FOLDER_NAME, DEFAULT_FOLFER_DESC, serviceContext);
        }
        
        return folder.getFolderId();
	}
	
	public static List<FileColumn> getFileColumns (final String fileId) {
		Long fileIdLong = Long.parseLong(fileId);
		List<FileColumn>  columns = new ArrayList<FileColumn>();
		
		FileEntry file = getFileEntry(fileIdLong);
		File rawFile;
		try {
			rawFile = DLFileEntryLocalServiceUtil.getFile(file.getUserId(), file.getFileEntryId(), file.getVersion(), false);
			CSVReader csvReader = new CSVReader(new FileReader(rawFile));

			String[] columnsRaw = csvReader.readNext();
			
			for (int i = 0; i < columnsRaw.length; i++) {
				FileColumn column = new FileColumn();
				column.setId(i);
				column.setName(columnsRaw[i]);
				
				columns.add(column);
			}
			
			csvReader.close();
		} catch (PortalException e) {
			LOG.error("Utils::getFileColumns Exception", e);
		} catch (SystemException e) {
			LOG.error("Utils::getFileColumns Exception", e);
		} catch (FileNotFoundException e) {
			LOG.error("Utils::getFileColumns Exception", e);
		} catch (Exception e) {
			LOG.error("Utils::getFileColumns Exception", e);
		}
		
		
		return columns;
	}
}
