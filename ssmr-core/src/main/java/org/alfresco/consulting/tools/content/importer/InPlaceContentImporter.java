package org.alfresco.consulting.tools.content.importer;


public class InPlaceContentImporter {


    /*
    *
    * curl -s -k -X POST --user 'admin':'admin' -F sourceDirectory='src-import' -F contentStore='default'  \
    * -F targetPath='/Company Home/Importazione' \
    http://localhost:8080/alfresco/service/bulkfsimport/inplace/initiate
*/
    public static void main(String[] args) {
//        txn = transactionService.getUserTransaction();
//        txn.begin();
//        AuthenticationUtil.setRunAsUser("admin");
//        InPlaceNodeImporterFactory inPlaceNodeImporterFactory = (InPlaceNodeImporterFactory)ctx.getBean("inPlaceNodeImporterFactory");
//        NodeImporter nodeImporter = inPlaceNodeImporterFactory.getNodeImporter("default", "2011");
//        BulkImportParameters bulkImportParameters = new BulkImportParameters();
//        bulkImportParameters.setTarget(folderNode);
//        bulkImportParameters.setReplaceExisting(true);
//        bulkImportParameters.setBatchSize(150);
//        bulkImportParameters.setNumThreads(4);
//        bulkImporter.bulkImport(bulkImportParameters, nodeImporter);
//        txn.commit();
    }
}
