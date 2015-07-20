package br.com.oncorp.listener;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("serial")
public class AjaxPhaseListener implements PhaseListener {

	public AjaxPhaseListener() {

	}

	@Override
	public void afterPhase(PhaseEvent pe) {

		FacesContext context = pe.getFacesContext();

		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

		String originalURL = (String) request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);

		String loginURL = request.getContextPath() + "/pages/login.xhtml";

		if (context.getPartialViewContext().isAjaxRequest() && originalURL == null && loginURL.equals(request.getRequestURI())) {

			try {

				context.getExternalContext().redirect(loginURL);

			} catch (IOException e) {

				throw new FacesException(e);
			}
		}
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {

	}

	@Override
	public PhaseId getPhaseId() {

		return PhaseId.ANY_PHASE;
	}

}
