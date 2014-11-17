package com.ep.mmbr.api.services;

public class ServiceFactory {

	/**
	 * Factory method to provide instance of the service
	 * @param service
	 * @return
	 */
	public Service getService(String service) {

		if (service.equals("GET")) {

			return new Get();
		} 
		 else if (service.equals("POST")) {
			return new Post();
		} 
		 else if (service.equals("PUT")) {
				return new Put();
			}
		else if (service.equals("DELETE")) {
			return new Delete();
		}
		

		else {

			return null;
		}

	}

}
