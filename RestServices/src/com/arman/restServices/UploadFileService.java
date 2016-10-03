package com.arman.restServices;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/file")
public class UploadFileService {
	
	private final String fileDirecory="c://temp/";
	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {

		String uploadedFileLocation = fileDirecory + fileDetail.getFileName();

		// save it
		writeToFile(uploadedInputStream, uploadedFileLocation);
		String filesInTheDirectory = listFilesInDirectory();
		String output = "File uploaded to : " + uploadedFileLocation+" \t    .Files that already exist(s) are: "+filesInTheDirectory;

		

		return Response.status(200).entity(output).build();

	}

	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {

		try {
			OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			// out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private String listFilesInDirectory() {
		File folder = new File(fileDirecory);
		File[] listOfFiles = folder.listFiles();
		String files = "";
		for (int i = 0; i < listOfFiles.length; i++) {
			files += i+"."+listOfFiles[i].getName() + "\t ";
		}
		return files;
	}

}