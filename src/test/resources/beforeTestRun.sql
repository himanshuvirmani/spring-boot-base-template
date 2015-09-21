/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;


DROP TABLE IF EXISTS `city`;

DROP TABLE IF EXISTS `hotel`;

DROP TABLE IF EXISTS `review`;

DROP TABLE IF EXISTS `review_history`;

DROP TABLE IF EXISTS `revinfo`;

CREATE TABLE `revinfo` (
  `rev`      INT(11) NOT NULL AUTO_INCREMENT,
  `revtstmp` BIGINT(20)       DEFAULT NULL,
  PRIMARY KEY (`rev`)
);


CREATE TABLE `review_history` (
  `id`        BIGINT(20) NOT NULL,
  `rev`       INT(11)    NOT NULL,
  `revtype`   TINYINT(4)    DEFAULT NULL,
  `details`   VARCHAR(5000) DEFAULT NULL,
  `title`     VARCHAR(255)  DEFAULT NULL,
  `trip_type` INT(11)       DEFAULT NULL,
  PRIMARY KEY (`id`, `rev`),
  FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
);


CREATE TABLE `city` (
  `id`      BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `country` VARCHAR(255) NOT NULL,
  `map`     VARCHAR(255) NOT NULL,
  `name`    VARCHAR(255) NOT NULL,
  `state`   VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `hotel` (
  `id`      BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `address` VARCHAR(255) NOT NULL,
  `name`    VARCHAR(255) NOT NULL,
  `zip`     VARCHAR(255) NOT NULL,
  `city_id` BIGINT(20)   NOT NULL,
  `status`  VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`city_id`) REFERENCES `city` (`id`)
);


CREATE TABLE `review` (
  `id`            BIGINT(20)    NOT NULL AUTO_INCREMENT,
  `check_in_date` DATE          NOT NULL,
  `details`       VARCHAR(5000) NOT NULL,
  `idx`           INT(11)       NOT NULL,
  `rating`        INT(11)       NOT NULL,
  `title`         VARCHAR(255)  NOT NULL,
  `trip_type`     INT(11)       NOT NULL,
  `hotel_id`      BIGINT(20)    NOT NULL,
  `created_at`    DATETIME               DEFAULT NULL,
  `updated_at`    DATETIME               DEFAULT NULL,
  `version`       BIGINT(20)             DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`id`)
);


INSERT INTO `city` (`id`, `country`, `map`, `name`, `state`)
VALUES
  (1, 'Australia', '-27.470933, 153.023502', 'Brisbane', 'Queensland'),
  (2, 'Australia', '-37.813187, 144.96298', 'Melbourne', 'Victoria'),
  (3, 'Australia', '-33.868901, 151.207091', 'Sydney', 'New South Wales'),
  (4, 'Canada', '45.508889, -73.554167', 'Montreal', 'Quebec'),
  (5, 'Israel', '32.066157, 34.777821', 'Tel Aviv', ''),
  (6, 'Japan', '35.689488, 139.691706', 'Tokyo', ''),
  (7, 'Spain', '41.387917, 2.169919', 'Barcelona', 'Catalunya'),
  (8, 'Switzerland', '46.992979, 6.931933', 'Neuchatel', ''),
  (9, 'UK', '51.381428, -2.357454', 'Bath', 'Somerset'),
  (10, 'UK', '51.500152, -0.126236', 'London', ''),
  (11, 'UK', '50.902571, -1.397238', 'Southampton', 'Hampshire'),
  (12, 'USA', '33.748995, -84.387982', 'Atlanta', 'GA'),
  (13, 'USA', '41.878114, -87.629798', 'Chicago', 'IL'),
  (14, 'USA', '44.811349, -91.498494', 'Eau Claire', 'WI'),
  (15, 'USA', '26.011201, -80.14949', 'Hollywood', 'FL'),
  (16, 'USA', '25.788969, -80.226439', 'Miami', 'FL'),
  (17, 'USA', '28.083627, -80.608109', 'Melbourne', 'FL'),
  (18, 'USA', '40.714353, -74.005973', 'New York', 'NY'),
  (19, 'USA', '28.034462, -80.588665', 'Palm Bay', 'FL'),
  (20, 'USA', '37.77493, -122.419415', 'San Francisco', 'CA'),
  (21, 'USA', '38.895112, -77.036366', 'Washington', 'DC');


INSERT INTO `hotel` (`id`, `address`, `name`, `zip`, `city_id`, `status`)
VALUES
  (1, 'William & George Streets', 'Conrad Treasury Place', '4001', 1, 'OPENED'),
  (2, '1 Southgate Ave, Southbank', 'The Langham', '3006', 2, 'OPENED'),
  (3, '68 Market Street', 'Swissotel', '2000', 3, 'OPENED'),
  (4, '1228 Sherbrooke St', 'Ritz Carlton', 'H3G1H6', 4, 'OPENED'),
  (5, 'Independence Park', 'Hilton Tel Aviv', '63405', 5, 'OPENED'),
  (6, 'Takeshiba Pier', 'InterContinental Tokyo Bay', '105', 6, 'OPENED'),
  (7, 'Passeig del Taulat 262-264', 'Hilton Diagonal Mar', '08019', 7, 'OPENED'),
  (8, ' Esplanade Leopold-Robert 2', 'Hotel Beaulac', '2000', 8, 'OPENED'),
  (9, 'Weston Road', 'The Bath Priory Hotel', 'BA1 2XT', 9, 'OPENED'),
  (10, 'Rossiter Road, Widcombe Basin', 'Bath Travelodge', 'BA2 4JP', 9, 'OPENED'),
  (11, 'Albany Street', 'Melia White House', 'NW1 3UP', 10, 'OPENED'),
  (12, 'The Cottage, Southampton Business Park', 'Chilworth Manor', 'SO16 7JF', 11, 'OPENED'),
  (13, 'Tower Place, Buckhead', 'Marriott Courtyard', '30305', 12, 'OPENED'),
  (14, 'Peachtree Rd, Buckhead', 'Ritz Carlton', '30326', 12, 'OPENED'),
  (15, 'Tower Place, Buckhead', 'Doubletree', '30305', 12, 'OPENED'),
  (16, '171 West Randolph Street', 'Hotel Allegro', '60601', 13, 'OPENED'),
  (17, '2106 N Clairemont Ave', 'Sea Horse Inn', '54703', 14, 'OPENED'),
  (18, '1151 W Macarthur Ave', 'Super 8 Eau Claire Campus Area', '54701', 14, 'OPENED'),
  (19, '3555 S. Ocean Drive', 'Westin Diplomat', '33019', 15, 'OPENED'),
  (20, '1395 Brickell Ave', 'Conrad Miami', '33131', 16, 'OPENED'),
  (21, '3101 North Hwy', 'Radisson Suite Hotel Oceanfront', '32903', 17, 'OPENED'),
  (22, 'Union Square, Manhattan', 'W Union Hotel', '10011', 18, 'OPENED'),
  (23, 'Lexington Ave, Manhattan', 'W Lexington Hotel', '10011', 18, 'OPENED'),
  (24, '70 Park Avenue', '70 Park Avenue Hotel', '10011', 18, 'OPENED'),
  (25, '890 Palm Bay Rd NE', 'Jameson Inn', '32905', 19, 'OPENED'),
  (26, '55 Fourth Street', 'Marriot Downtown', '94103', 20, 'OPENED'),
  (27, '1315 16th Street NW', 'Hotel Rouge', '20036', 21, 'OPENED');


INSERT INTO `review` (`id`, `check_in_date`, `details`, `idx`, `rating`, `title`, `trip_type`, `hotel_id`, `created_at`, `updated_at`, `version`)
VALUES
  (1, '2005-05-10', 'I stayed in 2005, the hotel was nice enough but nothing special.', 0, 2, 'Pretty average', 4, 2,
   NULL, NULL, NULL),
  (2, '2006-01-12', 'This hotel has a fantastic lovely big windows.  The room we stayed in had lots of space.  Recommended.', 1, 4, 'Bright hotel with big rooms', 2, 2, NULL, NULL, NULL),
  (3, '2006-05-25', 'I liked this hotel and would stay again.', 2, 3, 'Pretty good', 1, 2, NULL, NULL, NULL),
  (4, '2009-01-20', 'The rooms are maintained to a high standard and very clean, the bathroom was spotless!!', 3, 3, 'Nice clean rooms', 2, 2, NULL, NULL, NULL),
  (5, '2000-01-23', 'We stayed here after a wedding and it was fantastic.  Recommend to all.', 0, 4, 'A lovely hotel', 1, 9, NULL, NULL, NULL),
  (6, '2000-08-04', 'A very special hotel with lovely staff.', 1, 3, 'Very special', 1, 9, NULL, NULL, NULL),
  (7, '2001-01-01', 'Stayed during the summer heat wave (exceptional for England!) and the room was very hot.  Still recommended.', 2, 2, 'Nice but too hot', 4, 9, NULL, NULL, NULL),
  (8, '2002-01-20', 'Considering how central this hotel is the rooms are a very good size.', 3, 3, 'Big rooms and a great view', 1, 9, NULL, NULL, NULL),
  (9, '2002-11-03', 'A nice hotel but be prepared to pay over the odds for your stay.', 4, 2, 'Good but pricey', 1, 9, NULL, NULL, NULL),
  (10, '2003-09-18', 'Just lovely.', 5, 4, 'Fantastic place', 1, 9, NULL, NULL, NULL),
  (11, '2004-03-21', 'I stayed here in 2004 and found it to be very relaxing, a nice pool and good gym is cherry on the cake.', 6, 4, 'A very special place', 3, 9, NULL, NULL, NULL),
  (12, '2004-04-10', 'I complained after I was told I could not check out after 11pm.  Ridiculous!!!', 7, 0, 'Terrible', 0, 9, NULL, NULL, NULL),
  (13, '2004-12-20', 'Central location makes this a perfect hotel.  Be warned though, its not cheap.', 8, 4, 'A perfect location', 4, 9, NULL, NULL, NULL),
  (14, '2005-04-19', 'Dig deep into your pockets and enjoy this lovely City and fantastic hotel.', 9, 3, 'Expensive but worth it', 2, 9, NULL, NULL, NULL),
  (15, '2005-05-21', 'Top hotel in the area, would not stay anywhere else.', 10, 4, 'The best hotel in the area', 1, 9, NULL, NULL, NULL),
  (16, '2005-11-17', 'The garden upkeep run into thousands (perhaps explaining why the rooms are so much) but so lovely and relaxing.', 11, 4, 'Lovely hotel, fantastic grounds', 2, 9, NULL, NULL, NULL),
  (17, '2006-01-04', 'Top draw stuff.', 12, 3, 'Gorgeous Top Quality Hotel', 4, 9, NULL, NULL, NULL),
  (18, '2006-01-21', 'The food at this hotel is second to none, try the peppered steak!', 13, 4, 'Fabulous Hotel and Restaurant', 1, 9, NULL, NULL, NULL),
  (19, '2006-01-29', 'A lovely home away from home.', 14, 4, 'Feels like home', 4, 9, NULL, NULL, NULL),
  (20, '2006-03-21', 'Overpriced, Overpriced, Overpriced!!', 15, 1, 'Far too expensive', 1, 9, NULL, NULL, NULL),
  (21, '2006-05-10', 'The staff went out of their way to help us after we missed our last train home, organising a Taxi back to Newport even after we had checked out.', 16, 4, 'Excellent Hotel, Wonderful Staff', 1, 9, NULL, NULL, NULL),
  (22, '2007-09-11', 'If you want a relaxing stay, this is the place.', 17, 3, 'The perfect retreat', 2, 9, NULL, NULL, NULL),
  (23, '2008-06-01', 'As other reviews have noted, the staff in this hotel really are the best in Bath.', 18, 3, 'Lovely stay, fantastic staff', 3, 9, NULL, NULL, NULL),
  (24, '2009-05-14', 'We will stay again for sure.', 19, 4, 'Cant Wait to go back', 2, 9, NULL, NULL, NULL),
  (25, '2010-04-26', 'We won a trip here after entering a competition.  Not sure we would pay the full price but such a wonderful place.', 20, 4, 'Amazing Hotel', 1, 9, NULL, NULL, NULL),
  (26, '2010-10-26', 'The pool was closed, the chief was ill, the staff were rude my wallet is bruised!', 21, 2, 'Dissapointed', 2, 9, NULL, NULL, NULL),
  (27, '2002-08-21', 'One of the worst hotels that I have ever stayed in.', 0, 0, 'Terrible hotel', 2, 10, NULL, NULL, NULL),
  (28, '2003-01-28', 'The staff refused to help me with any aspect of my stay, I will not stay here again.', 1, 0, 'Rude and unpleasant staff', 0, 10, NULL, NULL, NULL),
  (29, '2004-06-17', 'Dont stay here!!', 2, 1, 'Below par', 0, 10, NULL, NULL, NULL),
  (30, '2005-07-12', 'The room was far too small and felt unclean.  Not recommended.', 3, 0, 'Small and Unpleasant', 1, 10, NULL, NULL, NULL),
  (31, '2006-01-07', 'This hotel has some rough edges but I challenge you to find somewhere cheaper.', 4, 1, 'Cheap if you are not fussy', 4, 10, NULL, NULL, NULL),
  (32, '2006-01-13', 'Just terrible!', 5, 0, 'Terrible', 2, 10, NULL, NULL, NULL),
  (33, '2006-03-25', 'My room smelt of damp and I found the socks of the previous occupant under my bed.', 6, 0, 'Smelly and dirty room', 0, 10, NULL, NULL, NULL),
  (34, '2006-04-09', 'Grim.  I would try elsewhere before staying here.', 7, 0, 'Grim', 4, 10, NULL, NULL, NULL),
  (35, '2006-08-01', 'Building work during the day and a disco at night.  Good grief!', 8, 1, 'Very Noisy', 3, 10, NULL, NULL, NULL),
  (36, '2009-01-03', 'This hotel is in serious need of refurbishment, the windows are rotting, the paintwork is tired and the carpets are from the 1970s.', 9, 1, 'Tired and falling down', 4, 10, NULL, NULL, NULL),
  (37, '2009-07-20', 'I would not put my dog up in this hotel.', 10, 0, 'Not suitable for human habitation', 0, 10, NULL, NULL, NULL),
  (38, '2010-05-20', 'Average place but useful if you need to commute', 11, 1, 'Conveient for the railway', 0, 10, NULL, NULL, NULL),
  (39, '2010-01-22', 'Some of the reviews seem a bit harsh, its not too bad for the price.', 12, 2, 'Not as bad as the reviews', 2, 10, NULL, NULL, NULL),
  (40, '2011-01-10', 'Looks like this hotel has had a major facelift.  If you have stayed before 2011 perhaps its time to give this hotel another try.  Very good value for money and pretty nice.', 13, 3, 'Reburished and nice', 1, 10, NULL, NULL, NULL),
  (41, '2009-01-20', 'Most other hotels is this area are a bit ropey, this one is actually pretty good.', 0, 3, 'Better than most', 0, 13, NULL, NULL, NULL),
  (42, '2006-01-12', 'Cheap, no fuss hotel.  Good if you are travelling on business and just need a place to stay.', 0, 2, 'No fuss hotel', 3, 15, NULL, NULL, NULL),
  (43, '2009-01-20', 'The area felt nice and safe but the rooms are a little on the small side', 1, 2, 'Nice area but small rooms', 2, 15, NULL, NULL, NULL),
  (44, '2009-12-15', 'Good value for money, cant really fault it.', 0, 3, 'Cheap and Recommended', 2, 16, NULL, NULL, NULL),
  (45, '2006-01-11', 'The hotel has a very bad reputation.  I would avoid it if I were you.', 0, 0, 'Avoid', 0, 19, NULL, NULL, NULL),
  (46, '2010-01-09', 'Fantastic access to all the local attractions mean you wont mind the small rooms.', 0, 3, 'Close to the local attractions', 2, 20, NULL, NULL, NULL),
  (47, '2010-09-10', 'Not expensive and very welcoming staff. I would stay again.', 1, 2, 'Good value and friendly', 2, 20, NULL, NULL, NULL),
  (48, '2005-06-15', 'I cant fault this hotel and I have stayed here many times.  Always friendly staff and lovely atmosphere.', 0, 3, 'A very nice hotel', 3, 21, NULL, NULL, NULL),
  (49, '2006-01-20', 'To complaints at all.', 1, 2, 'Comfortable and good value', 4, 21, NULL, NULL, NULL),
  (50, '2007-08-21', 'Better than a lot of hotels in the area and not too pricey.', 2, 3, 'Above average', 1, 21, NULL, NULL, NULL),
  (51, '2002-01-19', 'The city never sleeps and neither will you if you say here.  The rooms are small and the sound insulation is poor!', 0, 0, 'Too noisy, too small', 1, 22, NULL, NULL, NULL),
  (52, '2004-03-10', 'Far too much money for such a tiny room!', 1, 1, 'Overpriced', 4, 22, NULL, NULL, NULL),
  (53, '2007-04-11', 'Not brilliant but not too bad either.', 2, 2, 'So so, nothing special', 0, 22, NULL, NULL, NULL),
  (54, '2004-07-21', 'So close to the heart of the city.  Recommended.', 0, 3, 'Excellent location', 2, 23, NULL, NULL, NULL),
  (55, '2006-05-20', 'I cant fault this hotel, clean, good location and nice staff.', 1, 3, 'Very nice', 1, 23, NULL, NULL, NULL),
  (56, '2003-11-10', 'I own this hotel and I think it is pretty darn good.', 0, 4, 'Great!!', 1, 24, NULL, NULL, NULL),
  (57, '2005-10-20', 'This is the BEST hotel in Palm Bay, not complaints at all.', 0, 3, 'Fantastical', 2, 25, NULL, NULL, NULL),
  (58, '2006-01-12', 'I rate this hotel 5 stars, the best in the area by miles.', 1, 4, 'Top marks', 1, 25, NULL, NULL, NULL),
  (59, '2006-07-02', 'I stayed in late 2006 with work, the room was very small and the restaurant does not stay open very late.', 0, 2, 'Could be better', 3, 26, NULL, NULL, NULL),
  (60, '2008-07-01', 'My room was freezing cold, I would not recommend this place.', 1, 1, 'Brrrr cold!', 4, 26, NULL, NULL, NULL),
  (61, '2009-01-05', 'You cant really go wrong here for the money.  There may be better places to stay but not for this price.', 2, 3, 'Nice for money', 2, 26, NULL, NULL, NULL),
  (62, '2000-01-29',
   'I will never ever stay here again!!  They wanted extra cash to get fresh batteries for the TV remote', 0, 0,
   'Never again', 2, 27, NULL, NULL, NULL),
  (63, '2006-02-20',
   'This place is the pits, they charged us twice for a single night stay.  I only got refunded after contacting my credit card company.',
   1, 0, 'Avoid', 0, 27, NULL, NULL, NULL),
  (64, '2012-04-23', 'A nice hotel which I have not visited.', 1, 2, 'Himanshus 1st Review', 1, 9,
   '2015-09-15 12:08:30', '2015-09-15 12:08:30', 0);


INSERT INTO `revinfo` (`rev`, `revtstmp`)
VALUES
  (1, 1442299110393);

INSERT INTO `review_history` (`id`, `rev`, `revtype`, `details`, `title`, `trip_type`)
VALUES
  (64, 1, 0, 'A nice hotel which I have not visited.', 'Himanshu 1st Review', 1);

/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
