package no.ugland.utransprod.model;

import java.util.List;

import com.google.inject.internal.Lists;

public class VismaFile {

    private List<String> linjer = Lists.newArrayList();

    public VismaFile(List<String> linjer) {
	this.linjer = linjer;
    }

    public VismaFile() {
    }

    public List<String> getLinjer() {
	return linjer;
    }

}
