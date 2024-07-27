package com.Tasker.Controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for handling custom error pages.
 *
 * @author Yons Said
 */
@Controller
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    /**
     * Handles errors and returns the appropriate error page based on the status code.
     *
     * @param request the HttpServletRequest object.
     * @return the name of the error view.
     */
    @RequestMapping(PATH)
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/404";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "error/403";
            } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                return "error/401";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error/500";
            }
        }
        return "error/500";
    }

    /**
     * Returns the error path.
     *
     * @return the error path.
     */
    public String getErrorPath() {
        return PATH;
    }
}
