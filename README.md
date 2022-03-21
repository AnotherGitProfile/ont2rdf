# ONT2RDF

Simple utility to convert ontology from ONT to RDF format and serve it as SPARQL endpoint.

## Examples

Query all triples

```console
curl --request POST \
  --url http://localhost:3000/ds/sparql \
  --header 'Accept: application/sparql-results+json' \
  --header 'Content-Type: application/sparql-query' \
  --data 'SELECT * {
  ?s ?p ?o
}
'
```
