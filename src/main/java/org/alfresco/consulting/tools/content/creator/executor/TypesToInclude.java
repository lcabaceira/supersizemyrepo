package org.alfresco.consulting.tools.content.creator.executor;

public class TypesToInclude {
    private final Boolean pdf;
    private final Boolean ppt;
    private final Boolean xls;
    private final Boolean doc;
    private final Boolean jpg;

    public TypesToInclude(Boolean pdf, Boolean ppt, Boolean xls, Boolean doc, Boolean jpg) {
        this.pdf = pdf;
        this.ppt = ppt;
        this.xls = xls;
        this.doc = doc;
        this.jpg = jpg;
    }

    public Boolean getPdf() {
        return pdf;
    }

    public Boolean getPpt() {
        return ppt;
    }

    public Boolean getXls() {
        return xls;
    }

    public Boolean getDoc() {
        return doc;
    }

    public Boolean getJpg() {
        return jpg;
    }
}
