# Marketplace API
Marketplace API este un api destinat lucrului cu aplicațiile web ce implementează funcțiunile generice ale unui marketplace.

## Funcții
- Înscrierea și logarea utilizatorilor
- CRUD operații cu produsele utilizatorilor
- Paginarea produselor
- Like/dislike produse
- Notificarea utilizatorilor la schimbarea pretului produselor 

## Înscrierea și logarea utilizatorilor

#### Înscrierea utilizatorilor
``
POST /api/auth/signup
``
#### Logarea utilizatorilor
``
POST /api/auth/signin
``

## CRUD operații cu produsele utilizatorilor

#### Crearea produselor
``
POST /api/user/products
``
#### Afișarea produselor
``
GET /api/user/products
``
#### Actualizarea produselor
``
PUT /api/user/products/{product_id}
``
#### Ștergerea produselor
``
DELETE /api/user/products/{product_id}
``

## Paginarea produselor

#### Afișarea produselor paginate
``
GET /api/products
``

## Like/dislike produse

#### Like/dislike al unui produs
``
PATCH /api/products/{product_id}/{like}
``

## Notificarea utilizatorilor la schimbarea pretului produselor

#### Afișarea notificărilor
``
GET /api/user/notifications
``
#### Marcarea notificărilor ca fiind citite
``
PATCH /api/user/notifications/{notification_id}
``
#### Ștergerea notificărilor
``
DELETE /api/user/notifications/{notification_id}
``

## Dependențe
- POSTGRESQL
- REDIS
- JDK 17

## POSTGRESQL
POSGRESQL este baza de date principală a aplicației. Toate entitățile sunt înregistrate în POSTGRESQL.

## REDIS
Sesiunile de logare a utilizatorilor sunt tratate cu ajutorul bazei de date REDIS. Durata sesiunei poate fi modificată în proprietățile aplicației. 
```
token.timeout=5
```
Exemplul mai sus setează durata unei sesiuni în REDIS pe 5 minute.
