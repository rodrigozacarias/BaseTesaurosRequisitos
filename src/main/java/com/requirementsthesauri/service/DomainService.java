package com.requirementsthesauri.service;


import com.franz.agraph.jena.*;
import com.requirementsthesauri.model.Domain;
import com.requirementsthesauri.service.sparql.MethodsDomainSPARQL;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.apache.jena.vocabulary.RDF;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.json.*;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DomainService {

    Authentication authentication = new Authentication();
    MethodsDomainSPARQL methodsDomainSPARQL = new MethodsDomainSPARQL();
    AGUtils agUtils = new AGUtils();


    public ResponseEntity<?> createDomain(List<Domain> domainsList) throws Exception {

        authentication.getAuthentication();

        int TAM = domainsList.size();
        JsonArrayBuilder jsonArrayAdd = Json.createArrayBuilder();
        String uri = "localhost:8080/requirementsThesauri/domains/";

        for (int i = 0; i < TAM; i++) {
            String domainID = domainsList.get(i).getDomainID();
            String label = domainsList.get(i).getLabel();
            String prefLabel = domainsList.get(i).getPrefLabel();
            String altLabel = domainsList.get(i).getAltLabel();
            String description = domainsList.get(i).getDescription();
            String linkDBpedia = domainsList.get(i).getLinkDbpedia();
            String broaderDomainID = domainsList.get(i).getBroaderDomainID();
            List<String> narrowerDomainID = domainsList.get(i).getNarrowerDomainID();
            List<String> narrowerRequirementID = domainsList.get(i).getNarrowerRequirementID();


            String queryUpdate = methodsDomainSPARQL.insertDomainSparql(domainID, label, prefLabel, altLabel, description, linkDBpedia,
                    broaderDomainID, narrowerDomainID, narrowerRequirementID);

            UpdateRequest request = UpdateFactory.create(queryUpdate);
            UpdateProcessor up = UpdateExecutionFactory.createRemote(request, agUtils.sparqlEndpoint);
            up.execute();

            jsonArrayAdd.add(uri + domainID);

        }
        JsonArray ja = jsonArrayAdd.build();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JsonWriter writer = Json.createWriter(outputStream);
        writer.writeArray(ja);
        String output = new String(outputStream.toByteArray());
        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }
//        AGModel model = agUtils.getAGModel();
//
//        int TAM = domainsList.size();
//        JsonArrayBuilder jsonArrayAdd = Json.createArrayBuilder();
//
//        for (int i = 0; i < TAM; i++) {
//            Resource resource = model.createResource(agUtils.uriDom + domainsList.get(i).getDomainID());
//
//            //       skos:Concept
//            model.add(resource, model.getProperty(model.getNsPrefixURI("rdf") + "type"), model.createResource(model.getNsPrefixURI("skos") + "Concept"));
//
//            //       schema:url
//            model.add(resource, model.getProperty(agUtils.schema + "url"), resource);
//
//            //       rdfs:label
//            model.add(resource, model.getProperty(model.getNsPrefixURI("rdfs") + "label"), domainsList.get(i).getLabel());
//
//            //       skos:prefLabel
//            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "prefLabel"), domainsList.get(i).getPrefLabel());
//
//            //       skos:altLabel
//            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "altLabel"), domainsList.get(i).getAltLabel());
//
//            //       skos:note
//            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "note"), domainsList.get(i).getDescription());
//
//            //       owl:sameAs
//            model.add(resource, model.getProperty(model.getNsPrefixURI("owl") + "sameAs"), model.createResource(agUtils.dbr + domainsList.get(i).getLinkDbpedia()));
//
//            //       skos:broader
//            model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "broader"), model.createResource(agUtils.uriDom + domainsList.get(i).getBroaderDomainID()));
//            model.add(resource, model.getProperty(model.getNsPrefixURI("rdfs") + "subClassOf"), model.createResource(agUtils.uriDom + domainsList.get(i).getBroaderDomainID()));
//
//
//            for (String domainID : domainsList.get(i).getNarrowerDomainID()) {
//                model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "narrower"), model.createResource(agUtils.uriDom + domainID));
//            }
//
//            for (String reqID : domainsList.get(i).getNarrowerRequirementID()) {
//                model.add(resource, model.getProperty(model.getNsPrefixURI("skos") + "narrower"), model.createResource(agUtils.uriReq + reqID));
//            }
//
//            jsonArrayAdd.add(resource.getURI());
//
//        }
//
//
//        authentication.conn.close();
//
//        JsonArray ja = jsonArrayAdd.build();
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        JsonWriter writer = Json.createWriter(outputStream);
//        writer.writeArray(ja);
//        String output = new String(outputStream.toByteArray());
//        return new ResponseEntity<>(output, HttpStatus.CREATED);
//    }

    public Model getDomain(String domainURI) throws Exception {

            authentication.getAuthentication();


            AGModel model = agUtils.getAGModel();


            Model fakeModel = ModelFactory.createDefaultModel();
            fakeModel.setNsPrefix("rdf", model.getNsPrefixURI("rdf"));
            fakeModel.setNsPrefix("rdfs", model.getNsPrefixURI("rdfs"));
            fakeModel.setNsPrefix("schema", agUtils.schema);
            fakeModel.setNsPrefix("skos", model.getNsPrefixURI("skos"));
            fakeModel.setNsPrefix("owl", model.getNsPrefixURI("owl"));
            fakeModel.setNsPrefix("dbr", agUtils.dbr);
            fakeModel.setNsPrefix("uriDom", agUtils.uriDom);
            fakeModel.setNsPrefix("uriReq", agUtils.uriReq);


            String queryString = "Describe <" + domainURI + "> ?s ?p ?o ";

            AGReasoner reasoner = new AGReasoner();
            AGInfModel infmodel = new AGInfModel(reasoner, model);

            AGQuery query = AGQueryFactory.create(queryString);
            AGQueryExecution qe = AGQueryExecutionFactory.create(query, infmodel);


            Model results = qe.execDescribe();

            fakeModel.add(results);

            return fakeModel;

    }

    public ResponseEntity<?> getDomainDescribe(String domainID, String accept) throws Exception {

        Model fakeModel = getDomain(agUtils.uriDom + domainID);

        String format = agUtils.convertFromAcceptToFormat(accept);
        OutputStream stream = new ByteArrayOutputStream();
        if (format.isEmpty()){
            fakeModel.write(stream, "TURTLE");
        }else {
            fakeModel.write(stream, format);
        }
        return new ResponseEntity<>(stream.toString(), HttpStatus.OK);
    }



    public List<Domain> getAllDomains1() throws Exception {
        authentication.getAuthentication();

        String querySelect = methodsDomainSPARQL.getAllDomainsSparqlSelect();

        Query query = QueryFactory.create(querySelect);
        QueryExecution qexec = QueryExecutionFactory.sparqlService(agUtils.sparqlEndpoint, query);


        ResultSet results = qexec.execSelect();

        List<Domain> domains = new ArrayList<>();

        String c = "domain";
        while(results.hasNext()) {
            String uri = results.nextSolution().getResource(c).getURI();
            if(uri.contains("domains")) {

                Model model = getDomain(uri);

                String qSelect = methodsDomainSPARQL.getDomainSparqlSelect(uri);
                Query queryS = QueryFactory.create(qSelect);
//                QueryExecution qe = QueryExecutionFactory.create(queryS, model);
                QueryExecution qe = QueryExecutionFactory.createServiceRequest(agUtils.sparqlEndpoint, queryS);


                QuerySolution solution =  qe.execSelect().next();

                Domain domain = new Domain();

                domain.setUrl(solution.getResource("url").toString());
                domain.setLabel(solution.getLiteral("label").toString());
                domain.setPrefLabel(solution.getLiteral("prefLabel").toString());
                domain.setAltLabel(solution.getLiteral("altLabel").toString());
                domain.setDescription(solution.getLiteral("description").toString());
                domain.setLinkDbpedia(solution.getResource("linkDbpedia").toString());
                domain.setBroaderDomainID(solution.getResource("broaderDomainID").toString());

                qe.close();

//                String querySelectB = methodsDomainSPARQL.getDomainSparqlSelectBroader(uri);
//                Query querySB =QueryFactory.create(querySelectB);
//                QueryExecution qexecB = QueryExecutionFactory.create(querySB, model);
//                ResultSet resultsNB= qexecB.execSelect();
//
//                List<String> broaderDomainID = new ArrayList<>();
//                String b = "broaderDomainID";
//
//                while(resultsNB.hasNext()) {
//                    uri = resultsNB.nextSolution().getResource(b).getURI();
//                    broaderDomainID.add(uri);
//                }
//
//                qexecB.close();
//
//                String querySelectSC = methodsDomainSPARQL.getDomainSparqlSelectSubClass(uri);
//                Query querySC = QueryFactory.create(querySelectSC);
//                QueryExecution qexecSC = QueryExecutionFactory.create(querySC, model);
//                ResultSet resultsSC= qexecSC.execSelect();
//
//                List<String> subClassOf = new ArrayList<>();
//                String sc = "subClassOf";
//
//                while(resultsSC.hasNext()) {
//                    uri = resultsSC.nextSolution().getResource(sc).getURI();
//                    subClassOf.add(uri);
//                }
//
//                qexecSC.close();


                String querySelectN = methodsDomainSPARQL.getDomainSparqlSelectNarrower(uri);
                Query querySN = QueryFactory.create(querySelectN);
//                QueryExecution qexecN = QueryExecutionFactory.create(querySN, model);
                QueryExecution qexecN = QueryExecutionFactory.createServiceRequest(agUtils.sparqlEndpoint, querySN);
                ResultSet resultsN = qexecN.execSelect();

                List<String> narrowerDomainID = new ArrayList<>();
                List<String> narrowerRequirementID = new ArrayList<>();
//                List<String> dbpedia = new ArrayList<>();
                String n = "narrowerDomainID";

                while(resultsN.hasNext()) {
                    uri = resultsN.nextSolution().getResource(n).getURI();
                    if(uri.contains("domains")) {
                        narrowerDomainID.add(uri);
                    }else {
                        narrowerRequirementID.add(uri);
                    }
                }

                qexecN.close();

                domain.setNarrowerDomainID(narrowerDomainID);
                domain.setNarrowerRequirementID(narrowerRequirementID);
//                domain.setBroaderDomainID(broaderDomainID);
//                domain.setSubClassOf(subClassOf);
//                domain.setNarrowerDbpedia(dbpedia);

//            String narrowerRequirementID = soln.getResource("narrowerRequirementID").toString();

            domains.add(domain);



//                Model model = getDomain(uri);
//
//                NodeIterator nodes = model.listObjectsOfProperty(model.getProperty(agUtils.schema + "url"));
//
//
//
//                StmtIterator statements = model.listStatements();
//
//                while (nodes.hasNext()){
//                   RDFNode node = nodes.nextNode();
//
//                    System.out.println(node.toString());
//                }

            }
        }

////        JsonArrayBuilder jsonArrayAdd = Json.createArrayBuilder();
//
//        String c = "domain";
//        while(results.hasNext()) {
//            String uri = results.nextSolution().getResource(c).getURI();
//            if(uri.contains("domains")) {
//
//                Model model = getDomain(uri);
//
//                String qSelect = methodsDomainSPARQL.getDomainSparqlSelect(uri);
//                Query sparql = QueryFactory.create(qSelect);
//                QueryExecution qe = QueryExecutionFactory.create(sparql, model);
//
//                ResultSet rs = qe.execSelect();
//
//                System.out.println(rs.getResourceModel().listStatements().nextStatement().toString());
//                System.out.println(rs.nextSolution().toString());
//
//                QuerySolution solution = rs.nextSolution();
//
//                Domain domain = new Domain();
//
////                domain.setUrl(solution.getResource("url").toString());
////                domain.setLabel(solution.getLiteral("label").toString());
////                domain.setPrefLabel(solution.getLiteral("prefLabel").toString());
////                domain.setAltLabel(solution.getLiteral("altLabel").toString());
////                domain.setDescription(solution.getLiteral("description").toString());
////                domain.setLinkDbpedia(solution.getResource("linkDbpedia").toString());
////                domain.setDomainID(solution.getResource("broaderDomainID").toString());
//
//                String querySelectN = methodsDomainSPARQL.getDomainSparqlSelectNarrower(uri);
//                Query querySN = QueryFactory.create(querySelectN);
//                QueryExecution qexecN = QueryExecutionFactory.sparqlService(agUtils.sparqlEndpoint, querySN);
//                ResultSet resultsN = qexecN.execSelect();
//
//                List<String> narrowerDomainID = new ArrayList<>();
//                List<String> narrowerRequirementID = new ArrayList<>();
//                c = "narrowerDomainID";
//
//                while(resultsN.hasNext()) {
//                    uri = resultsN.nextSolution().getResource(c).getURI();
//                    if(uri.contains("domains")) {
//                        narrowerDomainID.add(uri);
//                    }else{
//                        narrowerRequirementID.add(uri);
//                    }
//                }
//
//                domain.setNarrowerDomainID(narrowerDomainID);
//                domain.setNarrowerRequirementID(narrowerRequirementID);
//
////            String narrowerRequirementID = soln.getResource("narrowerRequirementID").toString();
//
//            domains.add(domain);
//            }
//        }
////        JsonArray ja = jsonArrayAdd.build();
//
////        String c = "domain";
////        while(results.hasNext()) {
////            QuerySolution soln = results.nextSolution();
////            String uri = soln.getResource(c).getURI();
////            if(uri.contains("domains")) {
////                Domain domain = new Domain();
////
////                domain.setUrl(soln.getResource("url").toString());
////                domain.setLabel(soln.getLiteral("label").toString());
////                domain.setPrefLabel(soln.getLiteral("prefLabel").toString());
////                domain.setAltLabel(soln.getLiteral("altLabel").toString());
////                domain.setDescription(soln.getLiteral("description").toString());
////                domain.setLinkDbpedia(soln.getResource("linkDbpedia").toString());
////                domain.setDomainID(soln.getResource("broaderDomainID").toString());
////
////                domains.add(domain);
////            }
////        }

        return domains;
    }

    public List<Domain> getAllDomains() throws Exception {
        authentication.getAuthentication();

        String querySelect = methodsDomainSPARQL.getAllDomainsSparqlSelect();

        Query query = QueryFactory.create(querySelect);
        QueryExecution qexec = QueryExecutionFactory.sparqlService(agUtils.sparqlEndpoint, query);


        ResultSet results = qexec.execSelect();

        List<Domain> domains = new ArrayList<>();

        String c = "domain";
        while(results.hasNext()) {
            String uri = results.nextSolution().getResource(c).getURI();
            if(uri.contains("domains")) {

                Model model = getDomain(uri);

                String qSelect = methodsDomainSPARQL.getDomainSparqlSelect(uri);
                Query queryS = QueryFactory.create(qSelect);
                QueryExecution qe = QueryExecutionFactory.createServiceRequest(agUtils.sparqlEndpoint, queryS);


                QuerySolution solution =  qe.execSelect().next();

                Domain domain = new Domain();

                domain.setUrl(solution.getResource("url").toString());
                domain.setLabel(solution.getLiteral("label").toString());
//                domain.setPrefLabel(solution.getLiteral("prefLabel").toString());
//                domain.setAltLabel(solution.getLiteral("altLabel").toString());
//                domain.setDescription(solution.getLiteral("description").toString());
//                domain.setLinkDbpedia(solution.getResource("linkDbpedia").toString());
//                domain.setBroaderDomainID(solution.getResource("broaderDomainID").toString());

                qe.close();

                domains.add(domain);


            }
        }
        return domains;
    }


    public void deleteDomain(String domainURI) {
        authentication.getAuthentication();

        String deleteQuery = methodsDomainSPARQL.deleteDomainSparql(domainURI);


        UpdateRequest request = UpdateFactory.create(deleteQuery);
        UpdateProcessor up = UpdateExecutionFactory.createRemote(request, agUtils.sparqlEndpoint);
        up.execute();
    }

    public ResponseEntity<?> updateDomain(String oldDomainID, Domain newDomain) throws Exception {
        authentication.getAuthentication();

        deleteDomain(oldDomainID);
        List<Domain> domains = new ArrayList<>();
        domains.add(newDomain);

        return createDomain(domains);

    }

}


