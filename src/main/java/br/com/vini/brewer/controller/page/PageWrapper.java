package br.com.vini.brewer.controller.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.util.UriComponentsBuilder;

public class PageWrapper<T> {
	
	private Page<T> page;
	private UriComponentsBuilder uriBuilder;

	public PageWrapper(Page<T> page, HttpServletRequest request) {
		this.page = page;
//		this.uriBuilder = ServletUriComponentsBuilder.fromRequest(request);
		
		//Comentando linha acima a fim de corrigir a pesquisa com espaço
		String httpUrl = request.getRequestURL().append(
				request.getQueryString() != null ? "?" + request.getQueryString() : ""
		).toString().replaceAll("\\+", "%20");
		
		this.uriBuilder = UriComponentsBuilder.fromHttpUrl(httpUrl);
	}
	
	public List<T> getContent(){
		return this.page.getContent();
	}
	
	public boolean isEmpty() {
		return page.getContent().isEmpty();
	}
	
	public int getAtual() {
		return page.getNumber();
	}
	
	public boolean isFirst() {
		return page.isFirst();
	}
	
	public boolean isLast() {
		return page.isLast();
	}
	
	public int getTotal() {
		return page.getTotalPages();
	}
	
	public String urlForPage(int page) {
		return uriBuilder.replaceQueryParam("page", page).build(true).encode().toUriString();
	}
	
	public String urlOrdenada(String property) {
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
				.fromUriString(uriBuilder.build(true).encode().toString());
		
		String valorSort = property + "," + inverterDirecao(property);
		
		return uriComponentsBuilder.replaceQueryParam("sort", valorSort).build(true).encode().toUriString();
	}
	
	public String inverterDirecao(String property) {
		String direcao = "asc";
		
		Order order = page.getSort() != null ? page.getSort().getOrderFor(property) : null;
		if(order != null) {
			direcao = Sort.Direction.ASC.equals(order.getDirection()) ? "desc" : "asc";
		}
		
		return direcao;
	}
	
	public boolean descendente(String property) {
		return inverterDirecao(property).equals("asc");
	}
	
	public boolean ordenada(String property) {
		Order order = page.getSort() != null ? page.getSort().getOrderFor(property) : null;
		
		if(order == null) {
			return false;
		}
		
		return page.getSort().getOrderFor(property) != null ? true : false;
	}
}
