package telran.java53.security.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import telran.java53.accounting.dao.UserAccountRepository;
import telran.java53.accounting.model.Role;
import telran.java53.accounting.model.UserAccount;

@Component
@RequiredArgsConstructor
@Order(20)
public class AdminManagingRolesFilter implements Filter {

    private static final Role REQUIRED_ROLE = Role.ADMINISTRATOR;
  
    private final UserAccountRepository userAccountRepository;

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        if (isRoleManagementRequest(request.getMethod(), request.getServletPath())) {
            Optional<UserAccount> userAccountOpt = userAccountRepository.findById(request.getUserPrincipal().getName());

            if (userAccountOpt.isEmpty()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
                return;
            }

            UserAccount userAccount = userAccountOpt.get();

            if (!userAccount.getRoles().contains(REQUIRED_ROLE)) {
                response.sendError(403,"Access Denied");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private boolean isRoleManagementRequest(String method, String path) {
        return HttpMethod.PUT.matches(method) && path.matches("/account/user/\\w+/role/\\w+");
    }
}