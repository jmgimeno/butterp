# Versió curta

* L'escola vol organitzar un homenatge al Josep Maria
* Es vol organitzar un jurat
* T'apuntes?
* Voldria fer una minireunió per tal de començar a redactar els criteris i la orgaització (http://doodle.com/poll/peic2tnqaab5437s)

# Versió llarga

Fa temps que, des de la direcció de l'escola, s'està organitzant un acte d'homenatge al Josep Maria. 
Inicialment es volia fer al novembre, però entre els parcials i les avaluacions externes, no quedaven 
dates disponibles. AL final tot sembla indicar que es farà l'11 de febrer.

Una de les coses que es vol organitzar és un concurs de programació i, qui escriu, ha estat nomenat
per organitzar-lo. La unica cosa que em va venir al cap va ser implementar un intèrpret de Lisp (és 
més aviat scheme, però no crec que ningú noti la diferència). 

Quan es volia fer al novembre, part d'això es podria haver incorporat a l'assignatuar d'estructures de 
dades però amb les noves dates ja no es farà així.

## Implementació de referència

He fet una implementació per tal de veure una mica com guiar el disseny i quines parts els hi podem
donar fetes.

Ho he pujat a bitbucket (a un repositori privat) ja que el meu compte de GitHub el coneixen els alumnes. Us convidaré
per si li voleu donar un com d'ull. Si no teniu compte a bitbucket, o no voleu crear-ne un, us puc passar un ZIP amb 
el codi del projecte. L'he mavenitzat per a que no us calgui usar el mateix IDE que jo.

La meva implementació va una mica més enllà ja que carrega un fitxer amb funcions predefinides i permet 
definir nova sintaxi usant macros. 

## El concurs

Es participarà en grups de dues persones i, de cara a que participi el màxim de gent, hi haurà dos nivells:

* Nivell bàsic: gent de segon que ha fet ED (els de primer només han fet Programació I i poca cosa
poden fer)
* Niven avançat: tots els altres.

### Nivell bàsic

La meva idea seria que implementessin les estructures de dades bàsiques:

* SExpression, ConsCell, Integer, Symbol i ListOps (encara que no han d'implementar ni eval ni apply)
* Environment i NestedMap

### Nivell avançat

* Nivell bàsic (amb la implementació d'eval i apply)
* Function, Lambda i Special
* Primitives

### Part que els podem donar

* Tot el paquet reader (que conté el Parser i el Tokenizer)
* Repl
* Les classes de test (encara que fins que no arriban a tercer no coneixen JUnit).
