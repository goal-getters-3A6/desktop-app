-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : ven. 08 mars 2024 à 11:47
-- Version du serveur : 10.4.28-MariaDB
-- Version de PHP : 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `pidev`
--

-- --------------------------------------------------------

--
-- Structure de la table `abonnement`
--

CREATE TABLE `abonnement` (
  `idAb` int(11) NOT NULL,
  `montantAb` float NOT NULL,
  `dateExpirationAb` date NOT NULL,
  `codePromoAb` varchar(255) NOT NULL,
  `typeAb` varchar(255) NOT NULL,
  `idU` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `abonnement`
--

INSERT INTO `abonnement` (`idAb`, `montantAb`, `dateExpirationAb`, `codePromoAb`, `typeAb`, `idU`) VALUES
(2, 500, '2020-11-29', 'Go123', 'ordinaire', NULL),
(3, 50, '2024-03-14', 'GoFit20', 'Ordinaire', 19),
(4, 80, '2024-04-01', 'GoFit', 'Ordinaire', 19),
(5, 80, '2024-03-31', 'GoFit20', 'Ordinaire', 19);

-- --------------------------------------------------------

--
-- Structure de la table `avisequipement`
--

CREATE TABLE `avisequipement` (
  `idAEq` int(11) NOT NULL,
  `commAEq` varchar(255) DEFAULT NULL,
  `like` tinyint(1) DEFAULT NULL,
  `dislike` tinyint(1) DEFAULT NULL,
  `idEq` int(11) NOT NULL,
  `idUs` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `avisequipement`
--

INSERT INTO `avisequipement` (`idAEq`, `commAEq`, `like`, `dislike`, `idEq`, `idUs`) VALUES
(186, NULL, 0, 1, 46, 17),
(187, 'hi', 0, 0, 46, 17),
(190, 'pas mal', 0, 0, 47, 19),
(193, 'mauvaise', 0, 0, 47, 19),
(195, 'very good', 0, 0, 47, 19),
(196, NULL, 1, 0, 47, 19),
(197, NULL, 0, 1, 47, 19),
(199, 'good', 0, 0, 46, 19),
(200, 'very bad', 0, 0, 47, 19),
(201, NULL, 1, 0, 47, 19),
(202, 'bon', 0, 0, 46, 17),
(203, 'mauvaise', 0, 0, 46, 17),
(204, NULL, 1, 0, 46, 17),
(205, 'good', 0, 0, 47, 19),
(206, 'pas mal', 0, 0, 46, 19);

-- --------------------------------------------------------

--
-- Structure de la table `avisp`
--

CREATE TABLE `avisp` (
  `idAP` int(11) NOT NULL,
  `commAP` varchar(255) NOT NULL,
  `star` int(11) DEFAULT NULL,
  `fav` tinyint(1) NOT NULL,
  `idPlat` int(11) NOT NULL,
  `iduap` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `avisp`
--

INSERT INTO `avisp` (`idAP`, `commAP`, `star`, `fav`, `idPlat`, `iduap`) VALUES
(75, 'wow so good', 4, 1, 30, 14),
(76, 'nicee', 2, 1, 30, 14),
(77, 'yum!', 2, 1, 31, 14),
(78, 'meh', 2, 0, 32, 14),
(79, 'good', 5, 0, 33, 17),
(80, ' sur \"banana \":yeeeee3 maset', 1, 1, 30, 19);

-- --------------------------------------------------------

--
-- Structure de la table `equipement`
--

CREATE TABLE `equipement` (
  `idEq` int(11) NOT NULL,
  `nomEq` varchar(255) NOT NULL,
  `descEq` varchar(255) NOT NULL,
  `docEq` varchar(255) NOT NULL,
  `imageEq` varchar(255) NOT NULL,
  `categEq` varchar(255) NOT NULL,
  `noteEq` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `equipement`
--

INSERT INTO `equipement` (`idEq`, `nomEq`, `descEq`, `docEq`, `imageEq`, `categEq`, `noteEq`) VALUES
(46, 'Tapis de course', 'Un tapis de course est un équipement de fitness motorisé conçu pour la course ou la marche à l\'intérieur. Il se compose d\'une ceinture roulante qui se déplace à une vitesse réglable, offrant aux utilisateurs la possibilité de simuler une course en intérie', 'Commencez par allumer l\'appareil et ajustez la vitesse et l\'inclinaison selon vos préférences. En position centrale, démarrez en douceur et maintenez une posture stable en utilisant les poignées pour l\'équilibre.', 'C:/Users/Yosr/Downloads/desktop-app/src/main/resources/imgs/tapis.jpg', 'Fitness', 0),
(47, 'Bike', 'Un appareil de cardio-training conçu pour offrir une alternative pratique à la bicyclette traditionnelle. Doté d\'une selle réglable et de pédales fixées à des résistances ajustables.', 'Ajustez le siège à la hauteur appropriée, démarrez avec une résistance légère, puis asseyez-vous confortablement en maintenant une posture droite. Commencez à pédaler à un rythme modéré, augmentant graduellement la résistance pour intensifier votre entraî', 'C:/Users/Yosr/Downloads/desktop-app/src/main/resources/imgs/velo.jpg', 'Cardio-training', 0),
(54, 'Climber Step escalier', 'Le Climber Step est un équipement de fitness conçu pour simuler l\'effort de monter des escaliers, offrant ainsi un entraînement cardiovasculaire efficace tout en tonifiant les muscles des jambes, des fesses et du tronc.', ' Ajustez la résistance selon votre niveau, utilisez les poignées pour solliciter le haut du corps, et suivez les données du moniteur intégré. Commencez doucement et intensifiez progressivement. ', 'C:/Users/Yosr/Downloads/desktop-app/src/main/resources/imgs/escalier.jpg', 'Fitness', 0),
(55, 'Rack de squat', 'Le rack de squat est un dispositif de musculation sécurisé, avec des montants verticaux ajustables, permettant d\'effectuer des squats et de soulever des charges pour développer la force des jambes. Il offre une plateforme stable et polyvalente pour l\'entr', 'Ajustez la hauteur des supports en fonction de votre taille, positionnez la barre chargée de manière sécurisée, puis adoptez une posture stable. Lors de l\'exécution du squat, pliez les genoux et les hanches, descendez jusqu\'à ce que vos cuisses soient par', 'C:/Users/Yosr/Downloads/desktop-app/src/main/resources/imgs/musculation.png', 'Musculation', 0),
(56, 'Banc d’entraînement', 'un banc d\'entraînement est un équipement de fitness avec un dossier réglable, permettant une variété d\'exercices de musculation comme le développé couché. Il offre polyvalence pour l\'entraînement musculaire à domicile ou en salle de sport.', 'Pour commencer, montez le banc en suivant les instructions du fabricant. Ajustez le dossier et le siège pour les exercices spécifiques que vous souhaitez réaliser. Utilisez-le pour des mouvements classiques tels que le développé couché ou le curl biceps. ', 'C:/Users/Yosr/Downloads/desktop-app/src/main/resources/imgs/dos.jpg', 'Fitness', 0);

-- --------------------------------------------------------

--
-- Structure de la table `evenement`
--

CREATE TABLE `evenement` (
  `id_eve` int(11) NOT NULL,
  `nom_eve` varchar(255) NOT NULL,
  `date_deve` date NOT NULL,
  `date_feve` date NOT NULL,
  `nbr_max` int(11) DEFAULT NULL,
  `adresse_eve` varchar(255) NOT NULL,
  `image_eve` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `evenement`
--

INSERT INTO `evenement` (`id_eve`, `nom_eve`, `date_deve`, `date_feve`, `nbr_max`, `adresse_eve`, `image_eve`) VALUES
(78, 'course', '2024-03-21', '2024-03-29', 22, 'ariana', 'C:\\Users\\Yosr\\Downloads\\desktop-app\\src\\main\\resources\\imgs\\1.PNG');

-- --------------------------------------------------------

--
-- Structure de la table `participation`
--

CREATE TABLE `participation` (
  `id_p` int(11) NOT NULL,
  `nom_p` varchar(255) NOT NULL,
  `prenom_p` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `idf_event` int(11) NOT NULL,
  `age` int(11) NOT NULL,
  `id_User` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `participation`
--

INSERT INTO `participation` (`id_p`, `nom_p`, `prenom_p`, `email`, `idf_event`, `age`, `id_User`) VALUES
(71, 'hammami', 'salma', 'salma.hammemi@esprit.tn', 78, 33, 19),
(72, 'hammamia', 'salma', 'salma.hammemi@esprit.tn', 78, 43, 19);

-- --------------------------------------------------------

--
-- Structure de la table `plat`
--

CREATE TABLE `plat` (
  `idP` int(11) NOT NULL,
  `nomP` varchar(255) DEFAULT NULL,
  `prixP` float DEFAULT NULL,
  `descP` varchar(255) DEFAULT NULL,
  `alergieP` varchar(255) DEFAULT NULL,
  `etatP` tinyint(1) DEFAULT NULL,
  `photop` varchar(255) DEFAULT 'C:/Users/Yosr/Downloads/desktop-app/src/main/resources/imgs/pizza.PNG',
  `calories` int(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `plat`
--

INSERT INTO `plat` (`idP`, `nomP`, `prixP`, `descP`, `alergieP`, `etatP`, `photop`, `calories`) VALUES
(30, 'banana', 12.5, 'its literally just bananas and milk', 'contains lactose ', 1, 'C:/Users/Yosr/Downloads/desktop-app/src/main/resources/imgs/smoothie.PNG', 55),
(31, 'green salad', 5, 'its a salad idk it has vegetables olive oil and seasonings', 'none', 1, 'C:/Users/Yosr/Downloads/desktop-app/src/main/resources/imgs/salad.PNG', 100),
(32, 'pizza', 20, 'its a pizza but its healthy cause we said so', 'lactose , vegetable oil', 1, 'C:/Users/Yosr/Downloads/desktop-app/src/main/resources/imgs/pizza.PNG', 950),
(33, 'burritos', 16, 'its a burrito alright , i can promise you that', 'none', 0, 'C:/Users/Yosr/Downloads/desktop-app/src/main/resources/imgs/burritos.PNG', 520),
(34, 'oats', 7, 'oats are healthy please buy them', 'none', 1, 'C:/Users/Yosr/Downloads/desktop-app/src/main/resources/imgs/oats.PNG', 100);

-- --------------------------------------------------------

--
-- Structure de la table `reclamation`
--

CREATE TABLE `reclamation` (
  `idRec` int(11) NOT NULL,
  `categorieRec` varchar(255) NOT NULL,
  `descriptionRec` varchar(255) NOT NULL,
  `piéceJointeRec` varchar(255) NOT NULL,
  `oddRec` varchar(255) NOT NULL,
  `serviceRec` varchar(255) NOT NULL,
  `etatRec` int(11) NOT NULL,
  `idU` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `reclamation`
--

INSERT INTO `reclamation` (`idRec`, `categorieRec`, `descriptionRec`, `piéceJointeRec`, `oddRec`, `serviceRec`, `etatRec`, `idU`) VALUES
(1, 'Probléme Technique', 'Aqsdfghj.', 'ODD1', 'C:\\Users\\Yosr\\OneDrive - ESPRIT\\Bureau\\Git\\Atelier_Git_GitHub.pdf', 'Sécurité', 0, 19);

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

CREATE TABLE `reservation` (
  `idreservation` int(11) NOT NULL,
  `ids` int(11) NOT NULL,
  `nompersonne` varchar(255) NOT NULL,
  `prenompersonne` varchar(255) NOT NULL,
  `iduser` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `reservation`
--

INSERT INTO `reservation` (`idreservation`, `ids`, `nompersonne`, `prenompersonne`, `iduser`) VALUES
(200, 66, 'hammami', 'salma', 19),
(217, 80, 'hammami', 'salma', 19),
(224, 67, 'hammami', 'salma', 19),
(233, 80, 'hammami', 'salma', 19),
(238, 64, 'hammami', 'salma', 19),
(240, 67, 'hammami', 'salma', 19),
(241, 79, 'hammami', 'salma', 19),
(242, 67, 'louay', 'abidi', 24),
(249, 79, 'hammami', 'salma', 19);

-- --------------------------------------------------------

--
-- Structure de la table `seance`
--

CREATE TABLE `seance` (
  `idseance` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `horaire` time NOT NULL,
  `jourseance` varchar(255) NOT NULL,
  `numesalle` int(11) NOT NULL,
  `duree` varchar(255) NOT NULL,
  `imageseance` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `seance`
--

INSERT INTO `seance` (`idseance`, `nom`, `horaire`, `jourseance`, `numesalle`, `duree`, `imageseance`) VALUES
(64, 'Yoga', '19:00:00', 'Mardi', 5, '60min', 'C:/Users/Yosr/Downloads/desktop-app/src/main/resources/imgs/yoga.png'),
(66, 'Boxe', '16:00:00', 'Mardi', 4, '20min', 'C:/Users/Yosr/Downloads/desktop-app/src/main/resources/imgs/boxe.jpg'),
(67, 'Spinning', '20:00:00', 'Mercredi', 5, '20min', 'C:/Users/Yosr/Downloads/desktop-app/src/main/resources/imgs/spinning.jpg'),
(79, 'Bodyattack', '20:00:00', 'Dimanche', 2, '50min', 'C:/Users/Yosr/Downloads/desktop-app/src/main/resources/imgs/bodyattack.jpg'),
(80, 'Bodypump', '10:30:00', 'Lundi', 1, '30min', 'C:/Users/Yosr/Downloads/desktop-app/src/main/resources/imgs/bodypump.jpg'),
(81, 'Crossfit', '10:30:00', 'Mercredi', 2, '12min', 'C:/Users/Yosr/Downloads/desktop-app/src/main/resources/imgs/crossfit.jpg'),
(82, 'Bodyattack', '16:00:00', 'Mardi', 1, '46min', 'C:/Users/Yosr/Downloads/desktop-app/src/main/resources/imgs/bodyattack.jpg'),
(83, 'Crossfit', '20:00:00', 'Mercredi', 2, '30min', 'C:/Users/Yosr/Downloads/desktop-app/src/main/resources/imgs/crossfit.jpg'),
(84, 'Bodypump', '10:00:00', 'Jeudi', 3, '45min', 'C:/Users/Yosr/Downloads/desktop-app/src/main/resources/imgs/bodypump.jpg');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `prenom` varchar(255) NOT NULL,
  `mail` varchar(255) NOT NULL,
  `mdp` varchar(255) NOT NULL,
  `statut` tinyint(4) DEFAULT 0,
  `nb_tentative` int(11) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `date_naissance` date DEFAULT NULL,
  `date_inscription` date DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  `role` varchar(20) NOT NULL,
  `poids` float DEFAULT NULL,
  `taille` float DEFAULT NULL,
  `sexe` varchar(255) DEFAULT NULL,
  `tfa` int(1) DEFAULT NULL,
  `tfa_secret` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `nom`, `prenom`, `mail`, `mdp`, `statut`, `nb_tentative`, `image`, `date_naissance`, `date_inscription`, `tel`, `role`, `poids`, `taille`, `sexe`, `tfa`, `tfa_secret`) VALUES
(10, 'sarra', 'boukraa', 'sarra@gmail.com', 'b5654f3e5a16b9bb1f53b3942f076b70', 0, 0, NULL, '2023-05-23', '2024-02-25', '+376852369', 'CLIENT', 150.955, 101.274, 'Femme', NULL, NULL),
(13, 'sana', 'tounsi', 'sanatounsi@gmail.com', 'b8873a156dc35dc99b69d0f93ebe22fc', 0, 0, '', '2005-03-04', '2024-03-01', '+4930123456', 'ADMIN', 46.1538, 1.6226, 'Femme', NULL, NULL),
(14, 'hakimi', 'mayssa', 'mayssa.hakimi@esprit.tn', 'b3ec1bf03198b5837710317654b25a4c', 0, 0, '', '2003-02-28', '2024-03-01', '+21312325822', 'CLIENT', 50.4808, 1.44231, 'Femme', NULL, NULL),
(15, 'maryem', 'mounira', 'sarra.hammemi@esprit.tn', 'b5654f3e5a16b9bb1f53b3942f076b70', 0, 0, '', '2005-03-17', '2024-03-01', '+21630208751', 'CLIENT', 61.2981, 1.44231, 'Femme', NULL, NULL),
(16, 'bensmida', 'amine', 'amine.gggef@gmail.com', '202cb962ac59075b964b07152d234b70', 0, 0, '', '2008-02-29', '2024-03-01', '+1268568756', 'CLIENT', 38.762, 20.012, 'Homme', NULL, NULL),
(17, 'bensmida', 'selim', 'selim.dih@gmail.com', 'f48ac822376a54dbe8667a5b3a649058', 0, NULL, '', '2002-03-04', NULL, NULL, 'ADMIN', NULL, NULL, NULL, NULL, NULL),
(18, 'benhmida', 'imen', 'imen.benhmida@gmail.com', 'efb2e01ed433b79c785ef384a9bfc7e4', 0, 0, '', '1992-03-20', '2024-03-01', '+21627854000', 'CLIENT', 87.8005, 2.16346, 'Femme', NULL, NULL),
(19, 'hammami', 'salma', 'salma.hammemi@esprit.tn', 'f6852b2a3ac0cd7e69c801f69eddb57a', 1, 0, '', '2004-03-05', '2024-03-01', '+9312258774', 'CLIENT', 43.2692, 1.44231, '', NULL, NULL),
(20, 'John', 'Doe', 'john@gmail.com', '05a671c66aefea124cc08b76ea6d30bb', 0, 0, '', '1999-03-13', NULL, NULL, 'ADMIN', NULL, NULL, NULL, NULL, NULL),
(22, 'Test', 'test', 'test@gmail.com', '05a671c66aefea124cc08b76ea6d30bb', 0, 0, 'https://uc5b3a011872a619f586503845ea.dl.dropboxusercontent.com/cd/0/get/COedsCEwxNAFa4m2nWDQYiR2fUdqSuL2eY_6M2VNjBP3zw9Vk77hdm3MSv5OlQjabY3EYujpE2pYsBg3M7umliwJ3ElAbNDk-HpMs9rAniVh1u2lDkj74eynyCwQgD-2sO3p3x51YBxZsqy8yTvnfU2Pr6nukBUlZdM-tI2zEKFeWA/file', '1998-03-13', '2024-03-04', '+35522343454', 'CLIENT', 159.459, 187.387, 'Homme', NULL, NULL),
(24, 'louay', 'abidi', 'lou@esprit.tn', '4a7d1ed414474e4033ac29ccb8653d9b', 0, 0, 'https://uc14699cfa286bb070602f5c955f.dl.dropboxusercontent.com/cd/0/get/COmH3kBpBgEFi3kUdvTMbbspJ-l1ZOFI7wpl9Devrrzm3jzR5OxJ4pYOncUGrys4JFz5KBsLLp1GJMFjUbiRL6A3FA2yQNjYrhCf4f3XGlUp_J3R56loAa29nFNnTbbmJnNkwWVWjfevwzqTVe5gKp6mOrPGKbHyNNF8KqQ8XjIhkQ/file', '2002-03-13', '2024-03-06', '+21699256050', 'CLIENT', 78.7861, 197.055, 'Homme', NULL, NULL),
(26, 'maryem', 'maryem', 'mary@gmail.com', '098f6bcd4621d373cade4e832627b4f6', NULL, NULL, '', NULL, NULL, NULL, 'ADMIN', NULL, NULL, NULL, NULL, NULL),
(27, 'meryem', 'boukraa', 'meryemboukraa199@gmail.com', '05a671c66aefea124cc08b76ea6d30bb', 1, 0, 'https://uc24311ba26ba045489678b9abc9.dl.dropboxusercontent.com/cd/0/get/COpkSEiqfgq0poQlp80URvMxkKMR0hYV9B_6R34PzXv94UkLAQXQUwVSNQmBYtQ0LijtPwg8-AyfC9shdHxYstWJ1dbMKFimvSfJ8Qsl8A0P8vf4H5vVIIc7JTMwkClL4O_3T2L4kHgS8qc9liglYNVMOTPAHuPb-GaSYBrAsQoMwQ/file', '2002-03-20', '2024-03-07', '+37512444444', 'CLIENT', 112.5, 87.2596, 'Femme', 1, '7LYJVWF7I6WXASLEQPIEUZYY6A6QULN7');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `abonnement`
--
ALTER TABLE `abonnement`
  ADD PRIMARY KEY (`idAb`),
  ADD KEY `idu_pk1` (`idU`);

--
-- Index pour la table `avisequipement`
--
ALTER TABLE `avisequipement`
  ADD PRIMARY KEY (`idAEq`),
  ADD KEY `fk_idEq` (`idEq`),
  ADD KEY `fk_idUs` (`idUs`);

--
-- Index pour la table `avisp`
--
ALTER TABLE `avisp`
  ADD PRIMARY KEY (`idAP`),
  ADD KEY `toto` (`idPlat`),
  ADD KEY `iduap` (`iduap`);

--
-- Index pour la table `equipement`
--
ALTER TABLE `equipement`
  ADD PRIMARY KEY (`idEq`);

--
-- Index pour la table `evenement`
--
ALTER TABLE `evenement`
  ADD PRIMARY KEY (`id_eve`);

--
-- Index pour la table `participation`
--
ALTER TABLE `participation`
  ADD PRIMARY KEY (`id_p`),
  ADD KEY `id_eve` (`idf_event`),
  ADD KEY `user` (`id_User`);

--
-- Index pour la table `plat`
--
ALTER TABLE `plat`
  ADD PRIMARY KEY (`idP`);

--
-- Index pour la table `reclamation`
--
ALTER TABLE `reclamation`
  ADD PRIMARY KEY (`idRec`),
  ADD KEY `idu_pk2` (`idU`);

--
-- Index pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`idreservation`),
  ADD KEY `fk_idseance` (`ids`),
  ADD KEY `iduser` (`iduser`);

--
-- Index pour la table `seance`
--
ALTER TABLE `seance`
  ADD PRIMARY KEY (`idseance`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `abonnement`
--
ALTER TABLE `abonnement`
  MODIFY `idAb` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `avisequipement`
--
ALTER TABLE `avisequipement`
  MODIFY `idAEq` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=207;

--
-- AUTO_INCREMENT pour la table `avisp`
--
ALTER TABLE `avisp`
  MODIFY `idAP` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=81;

--
-- AUTO_INCREMENT pour la table `equipement`
--
ALTER TABLE `equipement`
  MODIFY `idEq` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=70;

--
-- AUTO_INCREMENT pour la table `evenement`
--
ALTER TABLE `evenement`
  MODIFY `id_eve` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=79;

--
-- AUTO_INCREMENT pour la table `participation`
--
ALTER TABLE `participation`
  MODIFY `id_p` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=73;

--
-- AUTO_INCREMENT pour la table `plat`
--
ALTER TABLE `plat`
  MODIFY `idP` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT pour la table `reclamation`
--
ALTER TABLE `reclamation`
  MODIFY `idRec` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `idreservation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=250;

--
-- AUTO_INCREMENT pour la table `seance`
--
ALTER TABLE `seance`
  MODIFY `idseance` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=86;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `abonnement`
--
ALTER TABLE `abonnement`
  ADD CONSTRAINT `idu_pk1` FOREIGN KEY (`idU`) REFERENCES `user` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `avisequipement`
--
ALTER TABLE `avisequipement`
  ADD CONSTRAINT `fk_idEq` FOREIGN KEY (`idEq`) REFERENCES `equipement` (`idEq`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_idUs` FOREIGN KEY (`idUs`) REFERENCES `user` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `avisp`
--
ALTER TABLE `avisp`
  ADD CONSTRAINT `avisp_ibfk_1` FOREIGN KEY (`idPlat`) REFERENCES `plat` (`idP`),
  ADD CONSTRAINT `avisp_ibfk_2` FOREIGN KEY (`iduap`) REFERENCES `user` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `participation`
--
ALTER TABLE `participation`
  ADD CONSTRAINT `participation_ibfk_1` FOREIGN KEY (`idf_event`) REFERENCES `evenement` (`id_eve`),
  ADD CONSTRAINT `user` FOREIGN KEY (`id_User`) REFERENCES `user` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `reclamation`
--
ALTER TABLE `reclamation`
  ADD CONSTRAINT `idu_pk2` FOREIGN KEY (`idU`) REFERENCES `user` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `fk_idseance` FOREIGN KEY (`ids`) REFERENCES `seance` (`idseance`) ON DELETE CASCADE,
  ADD CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`ids`) REFERENCES `seance` (`idseance`),
  ADD CONSTRAINT `reservation_ibfk_2` FOREIGN KEY (`iduser`) REFERENCES `user` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
