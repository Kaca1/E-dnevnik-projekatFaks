-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3307
-- Generation Time: Nov 30, 2021 at 06:22 PM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ors1_opp_2021_2022`
--

CREATE DATABASE IF NOT EXISTS `ors1_opp_2021_2022` ; 
USE `ors1_opp_2021_2022`;
-- --------------------------------------------------------

--
-- Table structure for table `izostanci`
--

CREATE TABLE `izostanci` (
  `id` int(11) NOT NULL,
  `ucenik_id` int(11) NOT NULL,
  `predmet_u_skoli_id` int(11) NOT NULL,
  `datum` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `izostanci`
--

INSERT INTO `izostanci` (`id`, `ucenik_id`, `predmet_u_skoli_id`, `datum`) VALUES
(1, 1, 11, '2021-11-01'),
(2, 3, 2, '2021-11-01'),
(3, 4, 2, '2021-11-01'),
(4, 4, 2, '2021-11-12'),
(5, 6, 5, '2021-11-04'),
(6, 6, 10, '2021-11-23'),
(7, 8, 1, '2021-11-09');

-- --------------------------------------------------------

--
-- Table structure for table `ocjena`
--

CREATE TABLE `ocjena` (
  `id` int(11) NOT NULL,
  `ucenik_id` int(11) NOT NULL,
  `predmet_u_skoli_id` int(11) NOT NULL,
  `ocjena` int(11) NOT NULL,
  `datum` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `ocjena`
--

INSERT INTO `ocjena` (`id`, `ucenik_id`, `predmet_u_skoli_id`, `ocjena`, `datum`) VALUES
(1, 1, 11, 4, '2021-10-12'),
(2, 1, 11, 3, '2021-10-21'),
(3, 1, 12, 2, '2021-10-07'),
(4, 1, 12, 3, '2021-10-21'),
(5, 1, 12, 3, '2021-10-30'),
(6, 2, 12, 5, '2021-10-21'),
(7, 4, 7, 4, '2021-10-13'),
(8, 4, 2, 5, '2021-10-21'),
(9, 5, 5, 5, '2021-10-21'),
(10, 5, 5, 5, '2021-11-18'),
(11, 5, 9, 4, '2021-10-13'),
(12, 7, 4, 5, '2021-11-21'),
(13, 7, 8, 3, '2021-10-21'),
(14, 7, 8, 5, '2021-11-15'),
(15, 8, 1, 2, '2021-10-02'),
(16, 8, 1, 3, '2021-10-23');

-- --------------------------------------------------------

--
-- Table structure for table `ocjena_predmeta`
--

CREATE TABLE `ocjena_predmeta` (
  `id` int(11) NOT NULL,
  `predmet_u_skoli_id` int(11) NOT NULL,
  `ucenik_id` int(11) NOT NULL,
  `pitanje_id` int(11) NOT NULL,
  `ocjena` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `ocjena_predmeta`
--

INSERT INTO `ocjena_predmeta` (`id`, `predmet_u_skoli_id`, `ucenik_id`, `pitanje_id`, `ocjena`) VALUES
(1, 12, 2, 1, 5),
(2, 12, 2, 2, 5),
(3, 12, 2, 3, 5),
(4, 12, 2, 4, 4),
(5, 11, 1, 1, 3),
(6, 11, 1, 2, 4),
(7, 11, 1, 3, 5),
(8, 11, 1, 4, 4),
(9, 12, 1, 1, 3),
(10, 12, 1, 2, 4),
(11, 12, 1, 3, 5),
(12, 12, 1, 4, 4),
(13, 2, 4, 1, 5),
(14, 2, 4, 2, 4),
(15, 2, 4, 3, 5),
(16, 2, 4, 4, 4);

-- --------------------------------------------------------

--
-- Table structure for table `pitanje`
--

CREATE TABLE `pitanje` (
  `id` int(11) NOT NULL,
  `pitanje` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pitanje`
--

INSERT INTO `pitanje` (`id`, `pitanje`) VALUES
(1, 'Nastava se odrzava redovno'),
(2, 'Nastavnik se ponasa korektno prema ucenicima'),
(3, 'Nastavnik predaje na zanimljiv nacin'),
(4, 'Nastavnik stvara ugodnu atmosferu na predavanjima');

-- --------------------------------------------------------

--
-- Table structure for table `predmet`
--

CREATE TABLE `predmet` (
  `id` int(11) NOT NULL,
  `naziv` varchar(45) NOT NULL,
  `razred` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `predmet`
--

INSERT INTO `predmet` (`id`, `naziv`, `razred`) VALUES
(1, 'Matematika', 6),
(2, 'Matematika', 7),
(3, 'Fizika', 6),
(4, 'Hemija', 7);

-- --------------------------------------------------------

--
-- Table structure for table `predmet_u_skoli`
--

CREATE TABLE `predmet_u_skoli` (
  `id` int(11) NOT NULL,
  `predmet_id` int(11) NOT NULL,
  `skola_id` int(11) NOT NULL,
  `profesor_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `predmet_u_skoli`
--

INSERT INTO `predmet_u_skoli` (`id`, `predmet_id`, `skola_id`, `profesor_id`) VALUES
(1, 1, 1, 1),
(2, 1, 2, 2),
(3, 1, 3, 1),
(4, 1, 3, 3),
(5, 2, 3, 3),
(6, 2, 2, 2),
(7, 3, 2, 1),
(8, 3, 3, 4),
(9, 4, 3, 4),
(10, 4, 3, 3),
(11, 2, 1, 1),
(12, 4, 1, 4);

-- --------------------------------------------------------

--
-- Table structure for table `pristupni_podaci`
--

CREATE TABLE `pristupni_podaci` (
  `id` int(11) NOT NULL,
  `korisnicko_ime` text NOT NULL,
  `email` text NOT NULL,
  `sifra` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pristupni_podaci`
--

INSERT INTO `pristupni_podaci` (`id`, `korisnicko_ime`, `email`, `sifra`) VALUES
(1, 'vukasin.markovic', 'vukasin.markovic@os.sveti.sava.org', '4b1a3595d9b50db479aa8d81a9dd781e'),
(2, 'dusko.tadic', 'dusko.tadic@os.sveti.sava.org', 'b753779654ad2dce2f8e467e46545f30'),
(3, 'jovana.markovic', 'jovana.markovic@os.sveti.sava.org', '531a477899faccd8120420d833256f71'),
(4, 'tina.golubovic', 'tina.golubovic@os.sveti.sava.org', '8923a51bcdeb69dfa9536d628d0a0c6e'),
(5, 'ana.testc', 'ana.testc@os.ppnjegos.org', 'e2bdbabfd7b0fa646c366ce563e3002b'),
(6, 'marko.vasic', 'marko.vasic@os.ppnjegos.org', 'd6bf57a2527a01fa0df6141db25d8f92'),
(7, 'tijana.boskovic', 'tijana.boskovic@os.ppnjegos.org', '8945b5bf7cd57650510427dc12145974'),
(8, 'marko.knezevic', 'marko.knezevic@os.sveti.sava.org', '81506ed83a15904ed12c86154263c6b4'),
(9, 'radomir.jankovic', 'radomir.jankovic@gmail.com', 'd193e2a65f3c39c781cf27eb43f69025'),
(10, 'milana.markovic', 'milana.markovic@gmail.com', 'b70f61483e1f5be6d50599a10528ed87'),
(11, 'marko.todorovic', 'marko.todorovic@gmail.com', '47a14d190fd9b390ed173c22598e6797'),
(12, 'dragana.milosevic', 'dragana.milosevic@gmail.com', '03832c95c5ff0bef535aaae5de0f1f43');

-- --------------------------------------------------------

--
-- Table structure for table `profesor`
--

CREATE TABLE `profesor` (
  `id` int(11) NOT NULL,
  `ime` text NOT NULL,
  `prezime` text NOT NULL,
  `pol` int(11) NOT NULL,
  `pristupni_podaci_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `profesor`
--

INSERT INTO `profesor` (`id`, `ime`, `prezime`, `pol`, `pristupni_podaci_id`) VALUES
(1, 'Radomir', 'Jankovic', 1, 9),
(2, 'Milanka', 'Markovic', 0, 10),
(3, 'Marko', 'Todorovic', 1, 11),
(4, 'Dragana', 'Milosevic', 0, 12);

-- --------------------------------------------------------

--
-- Table structure for table `skola`
--

CREATE TABLE `skola` (
  `id` int(11) NOT NULL,
  `naziv` varchar(45) NOT NULL,
  `grad` varchar(45) NOT NULL,
  `mjesto` varchar(45) NOT NULL,
  `drzava` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `skola`
--

INSERT INTO `skola` (`id`, `naziv`, `grad`, `mjesto`, `drzava`) VALUES
(1, 'Sveti Sava', 'Gacko', 'Gacko', 'Bosna i Hercegovina'),
(2, 'Sveti Sava', 'Gacko', 'Avtovac', 'Bosna i Hercegovina'),
(3, 'Petar II Pertovic Njegos', 'Bileca', 'Bileca', 'Bosna i Hercegovina');

-- --------------------------------------------------------

--
-- Table structure for table `ucenik`
--

CREATE TABLE `ucenik` (
  `id` int(11) NOT NULL,
  `ime` text NOT NULL,
  `prezime` text NOT NULL,
  `pol` int(11) NOT NULL,
  `pristupni_podaci_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `ucenik`
--

INSERT INTO `ucenik` (`id`, `ime`, `prezime`, `pol`, `pristupni_podaci_id`) VALUES
(1, 'Vukasin', 'Markovic', 1, 1),
(2, 'Dusko', 'Tadic', 1, 2),
(3, 'Jovana', 'Markovic', 0, 3),
(4, 'Tina', 'Golubovic', 0, 4),
(5, 'Ana', 'Tesic', 0, 5),
(6, 'Marko', 'Vasic', 1, 6),
(7, 'Tijana', 'Boskovic', 0, 7),
(8, 'Marko', 'Knezevic', 1, 8);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `izostanci`
--
ALTER TABLE `izostanci`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `ocjena`
--
ALTER TABLE `ocjena`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `ocjena_predmeta`
--
ALTER TABLE `ocjena_predmeta`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pitanje`
--
ALTER TABLE `pitanje`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `predmet`
--
ALTER TABLE `predmet`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `predmet_u_skoli`
--
ALTER TABLE `predmet_u_skoli`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pristupni_podaci`
--
ALTER TABLE `pristupni_podaci`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `profesor`
--
ALTER TABLE `profesor`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `skola`
--
ALTER TABLE `skola`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `ucenik`
--
ALTER TABLE `ucenik`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `izostanci`
--
ALTER TABLE `izostanci`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `ocjena`
--
ALTER TABLE `ocjena`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `ocjena_predmeta`
--
ALTER TABLE `ocjena_predmeta`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `pitanje`
--
ALTER TABLE `pitanje`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `predmet`
--
ALTER TABLE `predmet`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `predmet_u_skoli`
--
ALTER TABLE `predmet_u_skoli`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `pristupni_podaci`
--
ALTER TABLE `pristupni_podaci`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `profesor`
--
ALTER TABLE `profesor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `skola`
--
ALTER TABLE `skola`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `ucenik`
--
ALTER TABLE `ucenik`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
