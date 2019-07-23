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
				"  title: currency\n" + 
				"  description: moneda\n" + 
				"  version: \"1.3\"\n" + 
				"paths:\n" + 
				"  /currencies:\n" + 
				"    summary: Path used to manage the list of currencies.\n" + 
				"    description: The REST endpoint/path used to list and create zero or more `currency`\n" + 
				"      entities.  This path contains a `GET` and `POST` operation to perform the list\n" + 
				"      and create tasks, respectively.\n" + 
				"    get:\n" + 
				"      summary: List All currencies\n" + 
				"      description: Gets a list of all `currency` entities.\n" + 
				"      operationId: getcurrencies\n" + 
				"      responses:\n" + 
				"        200:\n" + 
				"          description: Successful response - returns an array of `currency` entities.\n" + 
				"          content:\n" + 
				"            application/json:\n" + 
				"              schema:\n" + 
				"                type: array\n" + 
				"                items:\n" + 
				"                  $ref: '#/components/schemas/currency'\n" + 
				"    post:\n" + 
				"      summary: Create a currency\n" + 
				"      description: Creates a new instance of a `currency`.\n" + 
				"      operationId: createcurrency\n" + 
				"      requestBody:\n" + 
				"        description: A new `currency` to be created.\n" + 
				"        content:\n" + 
				"          application/json:\n" + 
				"            schema:\n" + 
				"              $ref: '#/components/schemas/currency'\n" + 
				"        required: true\n" + 
				"      responses:\n" + 
				"        201:\n" + 
				"          description: Successful response.\n" + 
				"  /currencies/{id}:\n" + 
				"    summary: Path used to manage a single currency.\n" + 
				"    description: The REST endpoint/path used to get, update, and delete single instances\n" + 
				"      of an `currency`.  This path contains `GET`, `PUT`, and `DELETE` operations\n" + 
				"      used to perform the get, update, and delete tasks, respectively.\n" + 
				"    get:\n" + 
				"      summary: Get a currency\n" + 
				"      description: Gets the details of a single instance of a `currency`.\n" + 
				"      operationId: getcurrency\n" + 
				"      responses:\n" + 
				"        200:\n" + 
				"          description: Successful response - returns a single `currency`.\n" + 
				"          content:\n" + 
				"            application/json:\n" + 
				"              schema:\n" + 
				"                $ref: '#/components/schemas/currency'\n" + 
				"    put:\n" + 
				"      summary: Update a currency\n" + 
				"      description: Updates an existing `currency`.\n" + 
				"      operationId: updatecurrency\n" + 
				"      requestBody:\n" + 
				"        description: Updated `currency` information.\n" + 
				"        content:\n" + 
				"          application/json:\n" + 
				"            schema:\n" + 
				"              $ref: '#/components/schemas/currency'\n" + 
				"        required: true\n" + 
				"      responses:\n" + 
				"        202:\n" + 
				"          description: Successful response.\n" + 
				"    delete:\n" + 
				"      summary: Delete a currency\n" + 
				"      description: Deletes an existing `currency`.\n" + 
				"      operationId: deletecurrency\n" + 
				"      responses:\n" + 
				"        204:\n" + 
				"          description: Successful response.\n" + 
				"    parameters:\n" + 
				"    - name: id\n" + 
				"      in: path\n" + 
				"      description: A unique identifier for a `currency`.\n" + 
				"      required: true\n" + 
				"      schema:\n" + 
				"        type: string\n" + 
				"components:\n" + 
				"  schemas:\n" + 
				"    currency:\n" + 
				"      title: Root Type for currency\n" + 
				"      description: moneda\n" + 
				"      type: object\n" + 
				"      properties:\n" + 
				"        symbol:\n" + 
				"          type: string\n" + 
				"        value:\n" + 
				"          format: double\n" + 
				"          type: number\n" + 
				"      example: |-\n" + 
				"        {\n" + 
				"            \"symbol\":\"ARG\",\n" + 
				"            \"value\":45.9\n" + 
				"        }\n" + 
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
		Node nodeOpenApi = nodeRootOpenApi.get("currency").get("1.3");
		System.out.println(nodeOpenApi);

		Node nodeRootSwagger = new NodeRootSwaggerBean(swagger);
		Node nodeSwagger = nodeRootSwagger.get("currency").get("1.3");
		System.out.println(nodeSwagger);
		
		System.out.println(nodeOpenApi.equals(nodeSwagger));
		
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
