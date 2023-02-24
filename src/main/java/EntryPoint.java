import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.avaya.product.fetcher.ws.PrepareSiebelReportService;
import com.avaya.product.fetcher.ws.SendEmail;

import java.util.Timer;
import java.util.TimerTask;

@WebServlet("/EntryPoint")
public class EntryPoint extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext context = getServletContext();
		String appPath = context.getRealPath("");
		String javaFilePath = appPath + "WEB-INF/classes/resources";
		File javaFile = new File(javaFilePath);
		System.out.print("PATH=" + javaFilePath);
		String quarter = request.getParameter("quarter");
		String year = request.getParameter("year");
		String email = request.getParameter("email");
		response.setContentType("text/html");

		// Delete all the preexisting files
		File directory = new File(javaFilePath);
		File[] files = directory.listFiles();
		// Delete all files in the directory
		for (File file : files) {
			if (file.isFile()) {
				file.delete();
			}
		}

		PrepareSiebelReportService prepareSiebelReportService = PrepareSiebelReportService.getInstance();
		prepareSiebelReportService.startReportGeneration(quarter, year, javaFile);
		String filePath = null;
		File metricFile = null;
		File filesList[] = directory.listFiles();
		for (File file : filesList) {
			System.out.println("FILE : " + file.getName());
			if (file.getName().contains("EngineerMetrics.xlsx")) {
				System.out.println("Sending a mail to recepients");
				filePath = file.getAbsolutePath();
				metricFile = file;
				break;
			}
		}
		
	    // Set the content type and file name
	    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment;filename=\""+metricFile.getName()+"\"");

	    // Set the content length
	    File file = new File(filePath);
	    response.setContentLength((int) file.length());

	    // Stream the file contents to the response output stream
	    try (InputStream in = new FileInputStream(file); OutputStream out = response.getOutputStream()) {
	        byte[] buffer = new byte[4096];
	        int bytesRead;
	        while ((bytesRead = in.read(buffer)) != -1) {
	            out.write(buffer, 0, bytesRead);
	        }
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }

	    // Commit the response
//	    response.flushBuffer();
//	    new SendEmail().sendMail(metricFile.getAbsolutePath(), metricFile.getName());
		RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
		dispatcher.forward(request, response);
	}
}