# Résolution D’Un VRP Problème 
## Introduction générale
   Le problème dit des tournées de véhicules est une extension du problème du voyageur de commerce. 
 Il ne s’agit plus ici de trouver un cycle hamiltonien de coût minimal, mais la combinaison de plusieurs cycles minimisant le coût total de parcours.
 Ce problème est à peine plus récent que le TSP et fait également l’objet de nombreux modèles mathématiques de résolution selon le cas (contrainte sur la taille des cycles, contraintes sur la capacité des arcs etc.).
 Ici, nous ne présenterons qu’un modèle de résolution on utilise la méthode de recuit simuler. 
## C’est quoi Recuit Simuler ? 
  La méthode du recuit simulé est une généralisation de la méthode Monte-Carlo ;
son but est de trouver une solution optimale pour un problème donné. 
Elle a été mise au point par trois chercheurs de la société IBM : S. Kirkpatrick, C.D. Gelatt et M.P. Vecchi en 1983, et indépendamment par V. Cerny en 1985 à partir de l'algorithme de Metropolis ;
qui permet de décrire l'évolution d'un système thermodynamique.
L’idée principale du recuit simulé tel qu’il a été proposé par Metropolis en 1953 est de simuler le comportement de la matière dans le processus du recuit très largement utilisé dans la métallurgie. Le but est d’atteindre un état d’équilibre thermodynamique, cet état d’équilibre (où l’énergie est minimale) représente - dans la méthode du recuit simulé – la solution optimale d’un problème ; L’énergie du système sera calculée par une fonction coût (ou fonction objectif). La méthode va donc essayer de trouver la solution optimale en optimisant une fonction objective, pour cela, un paramètre fictif de température a été ajouté par Kirkpatrick, Gelatt et Vecchi. En gros le principe consiste à générer successivement des configurations à partir d'une solution initiale S0 et d'une température initiale T0 qui diminuera tout au long du processus jusqu'à atteindre une température finale ou un état d’équilibre (optimum global). 

## Adaptation de L’algorithme : 
### Voisinage : 
- Permutations intra route simple et multiple.
- Shift entre deux routes. 
- Cree nouveaux route. 
### Représentation d’une Solution : 
- [[d, ci, ..., ck], ..., [d, cj, ..., ck+1]]
### Evaluation : 
- Min $\sum_{k=1}^{m} d_k + 100 * max(0, c_k - cap) + 100 * max(0, m-v_total) $
