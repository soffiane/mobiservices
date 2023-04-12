## Projet Mobiservices
### Environnement
- Java 8 ou plus
- Spring boot
- Restez le plus simple possible, vous pouvez utiliser h2 pour la base de données.
- Angular 14 
### Critères d’évaluation
- Clean Code,
- Pattern Design,
- Test Unitaire
### Restitution
- Utilisation de github.
o-Pensez à faire plusieurs commits pour montrer comment vous avancez dans les devs.

### EXERCICE
#### Online Bus Ticket
Votre mission est de développer une Api Rest qui permet de réserver un billet de bus.
### Domain
    Client
     Un identifiant,
     Un nom,
     Une adresse email

    Bus
     Un numéro unique,
     Chaque bus a un seul trajet par jour,
     Un nombre de places par trajet,
     Une heure de départ du trajet,
     Un prix de trajet

    Réservation
     La date de trajet,
     Le numéro de bus,
     L‘id de client.

    Bill
     L’identifiant de réservation
     Le type de moyen de paiement.

### Features
 Back
L’api permet à l’utilisateur de :
- Lister, créer, trouver par identifiant, supprimer un Bus
- Lister, créer, trouver par identifiant, supprimer une Réservation
- Payer une réservation via son identifiant de réservation
- Trier les factures

Front
L’application permet à l’utilisateur de :
- Lister, ajouter, modifier, supprimer une réservation

### Business rules
- Une remise de 5% est appliquée quand le prix du trajet est supérieur à 100 euros
- Une réservation peut contenir plusieurs trajets
- Le paiement peut se faire par paypal en indiquant l’email ou par carte de crédit en indiquant
le numéro de carte et sa date d’expiration

On ne vous demande pas d’implémenter la méthode de paiement.
Exemple:
Boolean payReservation(int reservationId){ return true ;}
- La facture(bill) se crée automatiquement quand une réservation est payée