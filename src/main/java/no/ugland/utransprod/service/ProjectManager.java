package no.ugland.utransprod.service;

import no.ugland.utransprod.model.Project;

public interface ProjectManager {
    Project findByOrderNr(String orderNr);
}
