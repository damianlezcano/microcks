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
				"swagger: \"2.0\"\n" + 
				"info:\n" + 
				"  title: Calculo de Seguros\n" + 
				"  version: 1.0.0\n" + 
				"paths:\n" + 
				"  /calcular-seguro:\n" + 
				"    post:\n" + 
				"      consumes:\n" + 
				"      - application/json\n" + 
				"      produces:\n" + 
				"      - application/json\n" + 
				"      parameters:\n" + 
				"      - name: body\n" + 
				"        in: body\n" + 
				"        required: true\n" + 
				"        schema:\n" + 
				"          type: array\n" + 
				"          items:\n" + 
				"            $ref: '#/definitions/ttCalcSeguroRow'\n" + 
				"      responses:\n" + 
				"        \"202\":\n" + 
				"          description: respuesta\n" + 
				"          schema:\n" + 
				"            $ref: '#/definitions/QueueResponse'\n" + 
				"          examples:\n" + 
				"            application/json:\n" + 
				"              url: http://hosts/queue/CggKCwAJAgoFCQUFBA4BDQ\n" + 
				"              status: CREATED\n" + 
				"              eta: 120\n" + 
				"  /calcular-seguro/queue/{id}:\n" + 
				"    get:\n" + 
				"      produces:\n" + 
				"      - application/json\n" + 
				"      responses:\n" + 
				"        \"200\":\n" + 
				"          description: Tarea finalizada\n" + 
				"          schema:\n" + 
				"            $ref: '#/definitions/CalcSeguroResponse'\n" + 
				"          examples:\n" + 
				"            application/json:\n" + 
				"              result: true\n" + 
				"              errores:\n" + 
				"              - some text\n" + 
				"              - some text\n" + 
				"              opSegCalc: 3.55\n" + 
				"        \"204\":\n" + 
				"          description: Estado de la tarea\n" + 
				"        \"404\":\n" + 
				"          description: 'No existe '\n" + 
				"        \"410\":\n" + 
				"          description: Finalizo el tiempo de vida de la tarea encolada\n" + 
				"    parameters:\n" + 
				"    - name: id\n" + 
				"      in: path\n" + 
				"      required: true\n" + 
				"      type: string\n" + 
				"definitions:\n" + 
				"  CalcSeguroResponse:\n" + 
				"    title: Root Type for CalcSeguroResponse\n" + 
				"    description: \"\"\n" + 
				"    type: object\n" + 
				"    properties:\n" + 
				"      result:\n" + 
				"        type: boolean\n" + 
				"      errores:\n" + 
				"        type: array\n" + 
				"        items:\n" + 
				"          type: string\n" + 
				"      opSegCalc:\n" + 
				"        format: double\n" + 
				"        type: number\n" + 
				"    example:\n" + 
				"      result: true\n" + 
				"      errores: []\n" + 
				"      opSegCalc: 12.3\n" + 
				"  ttCalcSeguroRow:\n" + 
				"    title: Root Type for ttCalcSeguroRow\n" + 
				"    description: \"\"\n" + 
				"    type: object\n" + 
				"    properties:\n" + 
				"      MontoEquipo:\n" + 
				"        type: integer\n" + 
				"      Aseguradora:\n" + 
				"        type: integer\n" + 
				"      ClaseSeguro:\n" + 
				"        type: integer\n" + 
				"      TipoSeguro:\n" + 
				"        type: integer\n" + 
				"      Cobertura:\n" + 
				"        type: integer\n" + 
				"      MonedaMontoEq:\n" + 
				"        type: integer\n" + 
				"      Contrato:\n" + 
				"        type: integer\n" + 
				"      Cotizacion:\n" + 
				"        type: integer\n" + 
				"      Dador:\n" + 
				"        type: integer\n" + 
				"    example:\n" + 
				"      MontoEquipo: 1\n" + 
				"      Aseguradora: 1\n" + 
				"      ClaseSeguro: 1\n" + 
				"      TipoSeguro: 1\n" + 
				"      Cobertura: 1\n" + 
				"      MonedaMontoEq: 1\n" + 
				"      Contrato: 1\n" + 
				"      Cotizacion: 1\n" + 
				"      Dador: 1\n" + 
				"  QueueResponse:\n" + 
				"    title: Root Type for QueueResponse\n" + 
				"    description: Respuesta de Pedido en cola\n" + 
				"    type: object\n" + 
				"    properties:\n" + 
				"      id:\n" + 
				"        type: string\n" + 
				"    example:\n" + 
				"      id: ggKCwAJAgoFCQUFBA4BDQ\n" + 
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
