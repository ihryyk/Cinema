package controller;

import controller.command.CommandFactory;
import controller.command.ICommand;
import controller.command.authCommand.LogInPageCommand;
import controller.command.authCommand.LogOutCommand;
import controller.command.authCommand.RegisterPageCommand;
import exception.DaoOperationException;
import exception.TransactionException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/cinema")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class FrontController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     String forward =  handleRequest(request,response);
     request.getRequestDispatcher("/views/"+forward+".jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String redirect = handleRequest(request, response);
        response.sendRedirect(redirect);
    }

    private String handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ICommand iCommand = CommandFactory.getCommand(request);
        try {
            return iCommand.execute(request, response);
        } catch (DaoOperationException | TransactionException e) {
            try{
                response.sendRedirect("/cinema?command=ERROR_PAGE");
            } catch (IOException ex) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
