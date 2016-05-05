package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserBean;

public class LoginFilter implements Filter {
	String permitUrls[]=null;
	boolean ignore=false;
	String gotoUrl="LogIn.jsp";

	@Override
	public void destroy() {
		ignore=false;
		gotoUrl=null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse res=(HttpServletResponse)response;
		
		if(!ignore){
			if(!isPermitUrl(request)){
				HttpSession session = req.getSession();
				UserBean user = (UserBean) session.getAttribute("user");
				if(user==null){
					res.sendRedirect(gotoUrl);
					return;
					}
				}
			}			
		chain.doFilter(request, response);

	}
	
	public boolean isPermitUrl(ServletRequest request){
		boolean isPermit=false;
		if(permitUrls!=null&&permitUrls.length>0){
			for (int i = 0; i < permitUrls.length; i++) {
				if(permitUrls[i].equals(currentUrl(request))){
					isPermit=true;
					break;
					}
				}
			}
		return isPermit;
	}
	
	public String currentUrl(ServletRequest request){
		HttpServletRequest res=(HttpServletRequest) request;
		String task=request.getParameter("task");
		String path=res.getContextPath();
		String uri=res.getRequestURI();
		if(task!=null){
			uri=uri.substring(path.length(), uri.length())+"?"+"task="+task;
			}else{
				uri=uri.substring(path.length(), uri.length());
				}
		return uri;
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String ignore = filterConfig.getInitParameter("ignore");
		String permitUrls =filterConfig.getInitParameter("permitUrls");
		if ("1".equals(ignore) || "yes".equals(ignore)||"true".equals(ignore)) {
			this.ignore = true;
			}
		if(permitUrls!=null&&permitUrls.length()>0);
		this.permitUrls=permitUrls.split(",");
	}

}
