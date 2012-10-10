Simple-orm - Petit outil ORM basé sur un mapping XML
=======================================================

Projet d'exemple développé pour illustrer les TP à l'école EPITECH

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
* Ajout de fonctionnalité dans l'objet Condition
* ....

