package org.microcks;

import org.microcks.model.node.Node;
import org.microcks.model.node.openapi.NodeMethodOpenApiBean;
import org.microcks.model.node.openapi.NodeRootOpenApiBean;
import org.microcks.model.node.swagger.NodeMethodSwaggerBean;
import org.microcks.model.node.swagger.NodeRootSwaggerBean;

public class Main {

	public static void main(String[] args) throws Exception {
		m();
	}	
	
	public static Node m() throws Exception {
		String yaml = "---\n" + 
				"openapi: 3.0.2\n" + 
				"info:\n" + 
				"  title: petstore\n" + 
				"  version: 1.0.0\n" + 
				"  description: petstore\n" + 
				"  termsOfService: http://swagger.io/terms/\n" + 
				"  contact:\n" + 
				"    name: Swagger API Team\n" + 
				"    url: http://swagger.io\n" + 
				"    email: apiteam@swagger.io\n" + 
				"  license:\n" + 
				"    name: Apache 2.0\n" + 
				"    url: https://www.apache.org/licenses/LICENSE-2.0.html\n" + 
				"servers:\n" + 
				"- url: http://petstore.swagger.io/api\n" + 
				"paths:\n" + 
				"  /pets:\n" + 
				"    get:\n" + 
				"      parameters:\n" + 
				"      - style: form\n" + 
				"        name: tags\n" + 
				"        description: tags to filter by\n" + 
				"        schema:\n" + 
				"          type: array\n" + 
				"          items:\n" + 
				"            type: string\n" + 
				"        in: query\n" + 
				"        required: false\n" + 
				"      - name: limit\n" + 
				"        description: maximum number of results to return\n" + 
				"        schema:\n" + 
				"          format: int32\n" + 
				"          type: integer\n" + 
				"        in: query\n" + 
				"        required: false\n" + 
				"      responses:\n" + 
				"        \"200\":\n" + 
				"          content:\n" + 
				"            application/json:\n" + 
				"              schema:\n" + 
				"                type: array\n" + 
				"                items:\n" + 
				"                  $ref: '#/components/schemas/Pet'\n" + 
				"              examples:\n" + 
				"                laurent_cats:\n" + 
				"                  value:\n" + 
				"                  - id: 1\n" + 
				"                    name: Zaza\n" + 
				"                    tag: cat\n" + 
				"                  - id: 2\n" + 
				"                    name: Tigresse\n" + 
				"                    tag: cat\n" + 
				"                  - id: 3\n" + 
				"                    name: Maki\n" + 
				"                    tag: cat\n" + 
				"                  - id: 4\n" + 
				"                    name: Toufik\n" + 
				"                    tag: cat\n" + 
				"          description: pet response\n" + 
				"        default:\n" + 
				"          content:\n" + 
				"            application/json:\n" + 
				"              schema:\n" + 
				"                $ref: '#/components/schemas/Error'\n" + 
				"          description: unexpected error\n" + 
				"      operationId: findPets\n" + 
				"      description: |\n" + 
				"        Returns all pets from the system that the user has access to\n" + 
				"    post:\n" + 
				"      requestBody:\n" + 
				"        description: Pet to add to the store\n" + 
				"        content:\n" + 
				"          application/json:\n" + 
				"            schema:\n" + 
				"              $ref: '#/components/schemas/NewPet'\n" + 
				"            examples:\n" + 
				"              tigresse:\n" + 
				"                value:\n" + 
				"                  name: Tigresse\n" + 
				"                  tag: cat\n" + 
				"        required: true\n" + 
				"      responses:\n" + 
				"        \"200\":\n" + 
				"          content:\n" + 
				"            application/json:\n" + 
				"              schema:\n" + 
				"                $ref: '#/components/schemas/Pet'\n" + 
				"              examples:\n" + 
				"                tigresse:\n" + 
				"                  value:\n" + 
				"                    id: 2\n" + 
				"                    name: Tigresse\n" + 
				"                    tag: cat\n" + 
				"          description: pet response\n" + 
				"        default:\n" + 
				"          content:\n" + 
				"            application/json:\n" + 
				"              schema:\n" + 
				"                $ref: '#/components/schemas/Error'\n" + 
				"          description: unexpected error\n" + 
				"      operationId: addPet\n" + 
				"      description: Creates a new pet in the store.  Duplicates are allowed\n" + 
				"  /pets/{id}:\n" + 
				"    get:\n" + 
				"      parameters:\n" + 
				"      - name: id\n" + 
				"        description: ID of pet to fetch\n" + 
				"        schema:\n" + 
				"          format: int64\n" + 
				"          type: integer\n" + 
				"        in: path\n" + 
				"        required: true\n" + 
				"        examples:\n" + 
				"          zaza:\n" + 
				"            value: 1\n" + 
				"      responses:\n" + 
				"        \"200\":\n" + 
				"          content:\n" + 
				"            application/json:\n" + 
				"              schema:\n" + 
				"                $ref: '#/components/schemas/Pet'\n" + 
				"              examples:\n" + 
				"                zaza:\n" + 
				"                  value:\n" + 
				"                    id: 1\n" + 
				"                    name: Zaza\n" + 
				"                    tag: cat\n" + 
				"          description: pet response\n" + 
				"        default:\n" + 
				"          content:\n" + 
				"            application/json:\n" + 
				"              schema:\n" + 
				"                $ref: '#/components/schemas/Error'\n" + 
				"          description: unexpected error\n" + 
				"      operationId: findPetById\n" + 
				"      description: |-\n" + 
				"        Returns a user based on a single ID, if the user does not have\n" + 
				"        access to the pet\n" + 
				"    delete:\n" + 
				"      parameters:\n" + 
				"      - name: id\n" + 
				"        description: ID of pet to delete\n" + 
				"        schema:\n" + 
				"          format: int64\n" + 
				"          type: integer\n" + 
				"        in: path\n" + 
				"        required: true\n" + 
				"      responses:\n" + 
				"        \"204\":\n" + 
				"          description: pet deleted\n" + 
				"        default:\n" + 
				"          content:\n" + 
				"            application/json:\n" + 
				"              schema:\n" + 
				"                $ref: '#/components/schemas/Error'\n" + 
				"          description: unexpected error\n" + 
				"      operationId: deletePet\n" + 
				"      description: deletes a single pet based on the ID supplied\n" + 
				"    parameters:\n" + 
				"    - name: id\n" + 
				"      description: Pet identifier\n" + 
				"      schema:\n" + 
				"        type: integer\n" + 
				"      in: path\n" + 
				"      required: true\n" + 
				"components:\n" + 
				"  schemas:\n" + 
				"    Pet:\n" + 
				"      allOf:\n" + 
				"      - $ref: '#/components/schemas/NewPet'\n" + 
				"      - required:\n" + 
				"        - id\n" + 
				"        properties:\n" + 
				"          id:\n" + 
				"            format: int64\n" + 
				"            type: integer\n" + 
				"    NewPet:\n" + 
				"      required:\n" + 
				"      - name\n" + 
				"      properties:\n" + 
				"        name:\n" + 
				"          type: string\n" + 
				"        tag:\n" + 
				"          type: string\n" + 
				"    Error:\n" + 
				"      required:\n" + 
				"      - code\n" + 
				"      - message\n" + 
				"      properties:\n" + 
				"        code:\n" + 
				"          format: int32\n" + 
				"          type: integer\n" + 
				"        message:\n" + 
				"          type: string\n" + 
				"";
		
		String swagger = "{\n" + 
				"  \"swagger\" : \"2.0\",\n" + 
				"  \"info\" : {\n" + 
				"    \"version\" : \"1.3\",\n" + 
				"    \"title\" : \"currency\"\n" + 
				"  },\n" + 
				"  \"host\" : \"0.0.0.0\",\n" + 
				"  \"schemes\" : [ \"http\" ],\n" + 
				"  \"paths\" : {\n" + 
				"    \"/currencies\" : {\n" + 
				"      \"get\" : {\n" + 
				"        \"summary\" : \"Gets a list of all `currency` entities.\",\n" + 
				"        \"operationId\" : \"getcurrencies\",\n" + 
				"        \"produces\" : [ \"application/json\" ],\n" + 
				"        \"responses\" : {\n" + 
				"          \"200\" : {\n" + 
				"            \"description\" : \"Output type\",\n" + 
				"            \"schema\" : {\n" + 
				"              \"type\" : \"array\",\n" + 
				"              \"items\" : {\n" + 
				"                \"$ref\" : \"#/definitions/Currency\"\n" + 
				"              }\n" + 
				"            }\n" + 
				"          }\n" + 
				"        }\n" + 
				"      },\n" + 
				"      \"post\" : {\n" + 
				"        \"summary\" : \"Creates a new instance of a `currency`.\",\n" + 
				"        \"operationId\" : \"createcurrency\",\n" + 
				"        \"consumes\" : [ \"application/json\" ],\n" + 
				"        \"parameters\" : [ {\n" + 
				"          \"in\" : \"body\",\n" + 
				"          \"name\" : \"body\",\n" + 
				"          \"description\" : \"A new `currency` to be created.\",\n" + 
				"          \"required\" : true,\n" + 
				"          \"schema\" : {\n" + 
				"            \"$ref\" : \"#/definitions/Currency\"\n" + 
				"          }\n" + 
				"        } ],\n" + 
				"        \"responses\" : {\n" + 
				"          \"200\" : { }\n" + 
				"        }\n" + 
				"      }\n" + 
				"    },\n" + 
				"    \"/currencies/{id}\" : {\n" + 
				"      \"get\" : {\n" + 
				"        \"summary\" : \"Gets the details of a single instance of a `currency`.\",\n" + 
				"        \"operationId\" : \"getcurrency\",\n" + 
				"        \"produces\" : [ \"application/json\" ],\n" + 
				"        \"parameters\" : [ {\n" + 
				"          \"name\" : \"id\",\n" + 
				"          \"in\" : \"path\",\n" + 
				"          \"description\" : \"A unique identifier for a `currency`.\",\n" + 
				"          \"required\" : true,\n" + 
				"          \"type\" : \"string\"\n" + 
				"        } ],\n" + 
				"        \"responses\" : {\n" + 
				"          \"200\" : {\n" + 
				"            \"description\" : \"Output type\",\n" + 
				"            \"schema\" : {\n" + 
				"              \"$ref\" : \"#/definitions/Currency\"\n" + 
				"            }\n" + 
				"          }\n" + 
				"        }\n" + 
				"      },\n" + 
				"      \"put\" : {\n" + 
				"        \"summary\" : \"Updates an existing `currency`.\",\n" + 
				"        \"operationId\" : \"updatecurrency\",\n" + 
				"        \"consumes\" : [ \"application/json\" ],\n" + 
				"        \"parameters\" : [ {\n" + 
				"          \"name\" : \"id\",\n" + 
				"          \"in\" : \"path\",\n" + 
				"          \"description\" : \"A unique identifier for a `currency`.\",\n" + 
				"          \"required\" : true,\n" + 
				"          \"type\" : \"string\"\n" + 
				"        }, {\n" + 
				"          \"in\" : \"body\",\n" + 
				"          \"name\" : \"body\",\n" + 
				"          \"description\" : \"Updated `currency` information.\",\n" + 
				"          \"required\" : true,\n" + 
				"          \"schema\" : {\n" + 
				"            \"$ref\" : \"#/definitions/Currency\"\n" + 
				"          }\n" + 
				"        } ],\n" + 
				"        \"responses\" : {\n" + 
				"          \"200\" : { }\n" + 
				"        }\n" + 
				"      },\n" + 
				"      \"delete\" : {\n" + 
				"        \"summary\" : \"Deletes an existing `currency`.\",\n" + 
				"        \"operationId\" : \"deletecurrency\",\n" + 
				"        \"parameters\" : [ {\n" + 
				"          \"name\" : \"id\",\n" + 
				"          \"in\" : \"path\",\n" + 
				"          \"description\" : \"A unique identifier for a `currency`.\",\n" + 
				"          \"required\" : true,\n" + 
				"          \"type\" : \"string\"\n" + 
				"        } ],\n" + 
				"        \"responses\" : {\n" + 
				"          \"200\" : { }\n" + 
				"        }\n" + 
				"      }\n" + 
				"    }\n" + 
				"  },\n" + 
				"  \"definitions\" : {\n" + 
				"    \"Currency\" : {\n" + 
				"      \"type\" : \"object\",\n" + 
				"      \"properties\" : {\n" + 
				"        \"id\" : {\n" + 
				"          \"type\" : \"string\"\n" + 
				"        },\n" + 
				"        \"symbol\" : {\n" + 
				"          \"type\" : \"string\"\n" + 
				"        },\n" + 
				"        \"value\" : {\n" + 
				"          \"type\" : \"number\",\n" + 
				"          \"format\" : \"double\"\n" + 
				"        }\n" + 
				"      },\n" + 
				"      \"description\" : \"moneda\"\n" + 
				"    }\n" + 
				"  }\n" + 
				"}";
		
		Node nodeRootOpenApi = new NodeRootOpenApiBean(yaml);
		Node nodeOpenApi = nodeRootOpenApi.get("Calculo de Seguros").get("1.0.0");
		System.out.println(nodeOpenApi);

//		Node nodeRootSwagger = new NodeRootSwaggerBean(swagger);
//		Node nodeSwagger = nodeRootSwagger.get("currency").get("1.3");
//		System.out.println(nodeSwagger);
//		
//		System.out.println(nodeOpenApi.equals(nodeSwagger));
		
		return nodeRootOpenApi;
		
//		System.out.println(nodeRoot);
		
//		HashMap info = (HashMap) item.get("info");
//		
//		String title = (String) info.get("title");
//		String version = (String) info.get("version");
//		
//		Node nodeService = new NodeServiceBean(title);
//		
//		HashMap paths = (HashMap) item.get("paths");
//		
//		Node nodeVersion = new NodeVersionBean(version, paths);
//		nodeService.add(nodeVersion);
//		
//		Node nodeRoot = new NodeRootBean();
//		nodeRoot.add(nodeService);
//		
//		System.out.println(nodeRoot.get());

	}
}
