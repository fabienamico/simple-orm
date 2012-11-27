Simple-orm - Petit outil ORM basé sur un mapping XML
=======================================================

Projet d'exemple développé pour illustrer un TP de formation

Utilisation
-------------

```java
Criteria<Personne> criteria = new Criteria<Personne>(Personne.class);
Personne p = criteria.save(personne);
List<Personne> personnes = criteria.list();
List<Personne> personnes = criteria.add(Condition.eq("nom", name))
				.add(Condition.between("dateNaissance", minDate, maxDate))
				.list();
```

A Faire
---------
* Gestion des transactions
* Gestion des erreurs
* Gestion des relations avec objet null
* Ajout de fonctionnalité dans l'objet Condition
* ....

