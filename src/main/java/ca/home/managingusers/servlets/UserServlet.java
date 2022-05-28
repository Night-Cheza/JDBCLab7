package ca.home.managingusers.servlets;

import ca.home.managingusers.models.Role;
import ca.home.managingusers.models.User;
import ca.home.managingusers.services.UserService;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Leila Nalivkina, Nick Hemnett
 */
public class UserServlet extends HttpServlet {
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserService uService = new UserService();

		try {
			List<User> users = uService.getAll();

			request.setAttribute("users", users);

			getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
		} catch(Exception e) {
			Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, e);
		}	
	}
	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		UserService uService = new UserService();

		try {
			List<User> users = uService.getAll();

			request.setAttribute("users", users);
		} catch(Exception e) {
			Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, e);
		}

		String action = request.getParameter("action");	

		String email;
		String firstName;
		String lastName;
		String password;
		String roleName;
		int roleID;
		boolean active = false;

		if(action != null && action.equals("add")) {
			email = request.getParameter("addEmail");
			firstName = request.getParameter("addFName");
			lastName = request.getParameter("addLName");
			password = request.getParameter("addPassword");
			roleName = request.getParameter("addRole");
			active = true;

			if(roleName.contains("sys")) {
				roleID = 1;
			} else
				if(roleName.contains("user")) {
					roleID = 2;
				} else {
					roleID = 3;
				}

			Role role = new Role(roleID, roleName);

			if(!email.isEmpty()) {
				try {
					uService.insert(email, active, firstName, lastName, password, role);

				request.setAttribute("message", "Record has been added");
				} catch(Exception e) {
					Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, e);
				}	
			}	
		} else
			if(action != null && action.equals("edit")) {
				String button = request.getParameter("editBtn");
				User user = new User();

				try {
					user = uService.get(button);

					request.setAttribute("user", user);
					request.setAttribute("editEmail", user.getEmail());
					request.setAttribute("editFName", user.getFirstName());
					request.setAttribute("editLName", user.getLastName());
					request.setAttribute("editPassword", user.getPassword());
					request.setAttribute("editRole", user.getRole().getRoleName());
					request.setAttribute("editActive", "true");
				} catch(Exception e) {
					Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, e);
				}

			} else 
				if(action != null && action.equals("update")){
					String button = request.getParameter("btn");

					if(button.contentEquals("save")) {
						String newEmail = request.getParameter("editEmail");
						String newFName = request.getParameter("editFName");
						String newLName = request.getParameter("editLName");
						String newPassword = request.getParameter("editPassword");
						String newRole = request.getParameter("editRole");

						if(newRole.contains("sys")) {
							roleID = 1;
						} else
							if(newRole.contains("user")) {
								roleID = 2;
							} else {
								roleID = 3;
							}

						String newActive = request.getParameter("editActive");

						if(newActive.equals("true")) {
							active = true;
						}

						Role role = new Role(roleID, newRole);

						try {
							uService.update(newEmail, active, newFName, newLName, newPassword, role);

							request.setAttribute("message", "Record has been updated");
						} catch(Exception e) {
							Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, e);
						}	
					} else {
					}
				} else {
					try {
						String button = request.getParameter("deleteBtn");

						uService.delete(button);

						request.setAttribute("message", "Record has been deleted");
					} catch(Exception e) {
						Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, e);
					}
				}

		try {
			List<User> users = uService.getAll();

			request.setAttribute("users", users);

			getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
		} catch(Exception e) {
			Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, e);
		}		
	}
}
