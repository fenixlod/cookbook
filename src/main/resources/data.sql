/*
INSERT INTO recipe (id, name, description, preparation) VALUES
  (1, 'Супа пилешка', 'здравословна, без запържване/задушаване', ''),
  (2, 'Супа спаначена постна', '', 'В постния вариант да се прави с червен пипер, иначе е блудково на вкус и цвят.'),
  (3, 'Супа топчета', '', ''),
  (4, 'Супа спаначена', 'Със застройка', 'В малко мазнина се задушават лука и моркова. Добавя се измитата коприва (измития и нарязан спанак) и се обърква хубаво с моркова и лука. След това се налива топла вода, като количеството зависи от желаната гъстота на супата. След като копривата поври 15-20 минути се добавя картоф (или ориз) и малко преди да е готов се добавя фидето. Супата се овкусява със сол и се добавя джоджен на вкус. След като е готово и фидето супата се застройва със застройка, приготвена от ЖЪЛТЪКА, разбит с киселото мляко.'),
  (5, 'Борщ постен', 'украинска супа в постен вариант', ''),
  (6, 'Крем супа с червена леща', '', ''),
  (7, 'Содена питка', 'постна содена питка', 'Пресяваме брашното заедно със солта и бакпулвера.\nВ чашка смесваме содата и захарта, разбъркваме и смесваме с водата.\nПравим кладенче в брашното, добавяме олиото и започваме да наливаме по малко вода.\nЗамесваме тестото и месим 10 мин, докато се замеси меко лепкаво тесто.\nОставяме малко да почине 10 мин. и оформяме питката във форма по желание.\nСлагаме паричка и клонка дрен увити в алуминиево фолио и ги забождаме в тестото.\nВъв форма поръсваме обилно с брашно и слагаме обредният хляб.\nПечем в предварително загрята на 200*С фурна 40-50 мин.\nИзваждаме, заливаме с тънка струйка растителна мазнина, поръсваме с шарена сол, пипер и задушаваме с кърпа.\nРазчупва се питката от най-възрастният член на семейството, като първото парче е за дома и всяко следващо за членовете на семейството.\nВ който се падне паричката, той ще бъда благословен да издържа семейството и носи паричките в дома.\nДренът ще носи здраве на този, в който се е паднало късметчето.'),
  (8, 'Борщ', 'украинска супа в класически вариант', ''),
  (9, 'Крем супа с тиквички', '', 'Подходящи подправки - сол, черен пипер, дафинов лист, куркума, чесън, копър.'),
  (10, 'Супа със замразени зелечуци', '', 'Може да се разнообрази с нарязани маслини или по-различен замразен зеленчуков микс.'),
  (11, 'Баница с масло', 'С шупли', 'В купа се разбиват 4 -5 яйца ( според големината им)\nКъм тях се добавя 350-400 гр сирене едро натрошено  и се разбърква добре с яйцата.\nДобавят се 3 суп. л. брашно и 8 суп.л кисело мляко с добавена 1 кафена .л .равна сода за хляб в него .\nВсичко се разбърква много добре и към сместта се слага 1 малко разтопено пакетче масло125гр.  ( маслото да не е горещо).\nТавата за баницата се намазва с олио, фурната на печката се включва на 180 градуса да загрява , докато се приготвя баницата.\nВзима се 1 кора, намазва се със сместта и се навива на руло.(Тази баница се прави с по 1 брой навита кора на руло!!!- не слагайте по 2) Рулата се навиват в тавата плътно едно до друго.\nОтгоре баницата се залива с 1 разбито яйце ( ако ви е останало малко от сместта го разбийте в нея), добавя се 2 суп. л. прясно мляко към него  и се оставя залята за 10 мин. да поседи .\nПреди да се сложи във фурната отгоре се нарязват съвсем малки парченца масло( около 20-25 грама).\nПече се около 25-30 мин.\nСлед изваждане се обръща върху 2 купи, докато изстине.'),
  (12, 'Супа със свинско и зеленчуци', 'С червен пипер', 'С червен пипер'),
  (13, 'Рассольник', 'Супа с кисели краставички', ''),
  (14, 'Крем супа с гъби', '', 'Подходящи подправки - черен пипер, сол, дафинов лист, куркума, чесън, риган, копър.\nМоже да се направи с олио и/или масло.'),
  (15, 'Крем супа с броколи и/или карфиол', '', 'Може да се приготви с олио и/или масло.'),
  (16, 'Супа пилешка с грах', 'Супа със зрял грах', ''),
  (17, 'Супа с домати', '', 'Може да се приготви с пресни домати или от буркан, но домашен. Задължително е да се сложи много магданоз.');
  
INSERT INTO tag (id, value) VALUES
  (1, 'супа'),
  (2, 'постно'),
  (3, 'печиво'),
  (4, 'баница');
  
INSERT INTO recipe_tags (recipe_id, tag_id) VALUES
  (1, 1),
  (2, 1),
  (2, 2),
  (3, 1),
  (4, 1),
  (5, 1),
  (5, 2),
  (6, 1),
  (6, 2),
  (7, 2),
  (7, 3),
  (8, 1),
  (9, 1),
  (9, 2),
  (10, 1),
  (10, 2),
  (11, 4),
  (12, 1),
  (13, 1),
  (14, 1),
  (14, 2),
  (15, 1),
  (15, 2),
  (16, 1),
  (17, 1),
  (17, 2);
  
INSERT INTO ingredient (id, name, amount, unit) VALUES
  (1, 'пилешко', 0.0, 'COUNT'),
  (2, 'воденички', 0.0, 'COUNT'),
  (3, 'морков', 0.0, 'COUNT'),
  (4, 'лук стар', 0.0, 'COUNT'),
  (5, 'пиперка зелена', 0.0, 'COUNT'),
  (6, 'картоф', 0.0, 'COUNT'),
  (7, 'зеле прясно', 0.0, 'COUNT'),
  (8, 'картоф', 0.0, 'COUNT'),
  (9, 'морков', 0.0, 'COUNT'),
  (10, 'лук стар', 0.0, 'COUNT'),
  (11, 'спанак', 0.0, 'COUNT'),
  (12, 'фиде', 0.0, 'COUNT'),
  (13, 'кайма смес', 0.0, 'COUNT'),
  (14, 'картоф', 0.0, 'COUNT'),
  (15, 'морков', 0.0, 'COUNT'),
  (16, 'лук стар', 0.0, 'COUNT'),
  (17, 'пиперка зелена', 0.0, 'COUNT'),
  (18, 'спанак', 500.0, 'WEIGHT'),
  (19, 'картоф', 0.0, 'COUNT'),
  (20, 'лук стар', 0.0, 'COUNT'),
  (21, 'морков', 0.0, 'COUNT'),
  (22, 'фиде', 0.0, 'COUNT'),
  (23, 'мляко кисело', 200.0, 'WEIGHT'),
  (24, 'яйце', 1.0, 'COUNT'),
  (25, 'цвекло', 0.0, 'COUNT'),
  (26, 'морков', 0.0, 'COUNT'),
  (27, 'лук стар', 0.0, 'COUNT'),
  (28, 'зеле прясно', 0.0, 'COUNT'),
  (29, 'пиперка зелена', 0.0, 'COUNT'),
  (30, 'пиперка червена', 0.0, 'COUNT'),
  (31, 'пюре доматено', 0.0, 'COUNT'),
  (32, 'боб зрял', 0.0, 'COUNT'),
  (33, 'картоф', 0.0, 'COUNT'),
  (34, 'олио', 0.0, 'COUNT'),
  (35, 'леща червена', 0.0, 'COUNT'),
  (36, 'морков', 0.0, 'COUNT'),
  (37, 'лук стар', 0.0, 'COUNT'),
  (38, 'пиперка зелена', 0.0, 'COUNT'),
  (39, 'пиперка червена', 0.0, 'COUNT'),
  (40, 'брашно', 500.0, 'WEIGHT'),
  (41, 'вода', 300.0, 'VOLUME'),
  (42, 'сол', 1.0, 'TEA_SPOON'),
  (43, 'захар', 1.0, 'SOUP_SPOON'),
  (44, 'бакпулвер', 1.0, 'COUNT'),
  (45, 'олио', 3.0, 'SOUP_SPOON'),
  (46, 'сода', 1.0, 'TEA_SPOON'),
  (47, 'цвекло', 0.0, 'COUNT'),
  (48, 'морков', 0.0, 'COUNT'),
  (49, 'лук стар', 0.0, 'COUNT'),
  (50, 'зеле прясно', 0.0, 'COUNT'),
  (51, 'пиперка зелена', 0.0, 'COUNT'),
  (52, 'пиперка червена', 0.0, 'COUNT'),
  (53, 'пюре доматено', 0.0, 'COUNT'),
  (54, 'боб зрял', 0.0, 'COUNT'),
  (55, 'картоф', 0.0, 'COUNT'),
  (56, 'олио', 0.0, 'COUNT'),
  (57, 'рагу', 0.0, 'COUNT'),
  (58, 'картоф', 0.0, 'COUNT'),
  (59, 'морков', 0.0, 'COUNT'),
  (60, 'лук стар', 0.0, 'COUNT'),
  (61, 'тиквичка', 0.0, 'COUNT'),
  (62, 'замразени зеленчуци', 0.0, 'COUNT'),
  (63, 'картоф', 0.0, 'COUNT'),
  (64, 'лук стар', 0.0, 'COUNT'),
  (65, 'фиде', 0.0, 'COUNT'),
  (66, 'яйце', 5.0, 'COUNT'),
  (67, 'сирене краве', 400.0, 'WEIGHT'),
  (68, 'мляко кисело', 8.0, 'SOUP_SPOON'),
  (69, 'брашно', 3.0, 'SOUP_SPOON'),
  (70, 'сода', 1.0, 'COFFE_SPOON'),
  (71, 'масло краве', 150.0, 'WEIGHT'),
  (72, 'кори за баница', 1.0, 'COUNT'),
  (73, 'мляко прясно', 2.0, 'SOUP_SPOON'),
  (74, 'рагу', 0.0, 'COUNT'),
  (75, 'замразени зеленчуци', 0.0, 'COUNT'),
  (76, 'рагу', 0.0, 'COUNT'),
  (77, 'картоф', 0.0, 'COUNT'),
  (78, 'морков', 0.0, 'COUNT'),
  (79, 'лук стар', 0.0, 'COUNT'),
  (80, 'краставица кисела', 0.0, 'COUNT'),
  (81, 'ечемик', 0.0, 'COUNT'),
  (82, 'олио', 0.0, 'COUNT'),
  (83, 'картоф', 0.0, 'COUNT'),
  (84, 'морков', 0.0, 'COUNT'),
  (85, 'лук стар', 0.0, 'COUNT'),
  (86, 'гъби', 0.0, 'COUNT'),
  (87, 'олио', 0.0, 'COUNT'),
  (88, 'картоф', 0.0, 'COUNT'),
  (89, 'лук стар', 0.0, 'COUNT'),
  (90, 'морков', 0.0, 'COUNT'),
  (91, 'броколи', 0.0, 'COUNT'),
  (92, 'карфиол', 0.0, 'COUNT'),
  (93, 'пилешко', 0.0, 'COUNT'),
  (94, 'картоф', 0.0, 'COUNT'),
  (95, 'морков', 0.0, 'COUNT'),
  (96, 'лук стар', 0.0, 'COUNT'),
  (97, 'грах зрял', 0.0, 'COUNT'),
  (98, 'пиперка зелена', 0.0, 'COUNT'),
  (99, 'лук стар', 0.0, 'COUNT'),
  (100, 'домат', 0.0, 'COUNT'),
  (101, 'фиде', 0.0, 'COUNT');
    
INSERT INTO recipe_ingredients (recipe_id, ingredient_id) VALUES
  (1, 1),
  (1, 2),
  (1, 3),
  (1, 4),
  (1, 5),
  (1, 6),
  (1, 7),
  (2, 8),
  (2, 9),
  (2, 10),
  (2, 11),
  (2, 12),
  (3, 13),
  (3, 14),
  (3, 15),
  (3, 16),
  (3, 17),
  (4, 18),
  (4, 19),
  (4, 20),
  (4, 21),
  (4, 22),
  (4, 23),
  (4, 24),
  (5, 25),
  (5, 26),
  (5, 27),
  (5, 28),
  (5, 29),
  (5, 30),
  (5, 31),
  (5, 32),
  (5, 33),
  (5, 34),
  (6, 35),
  (6, 36),
  (6, 37),
  (6, 38),
  (6, 39),
  (7, 40),
  (7, 41),
  (7, 42),
  (7, 43),
  (7, 44),
  (7, 45),
  (7, 46),
  (8, 47),
  (8, 48),
  (8, 49),
  (8, 50),
  (8, 51),
  (8, 52),
  (8, 53),
  (8, 54),
  (8, 55),
  (8, 56),
  (8, 57),
  (9, 58),
  (9, 59),
  (9, 60),
  (9, 61),
  (10, 62),
  (10, 63),
  (10, 64),
  (10, 65),
  (11, 66),
  (11, 67),
  (11, 68),
  (11, 69),
  (11, 70),
  (11, 71),
  (11, 72),
  (11, 73),
  (12, 74),
  (12, 75),
  (13, 76),
  (13, 77),
  (13, 78),
  (13, 79),
  (13, 80),
  (13, 81),
  (13, 82),
  (14, 83),
  (14, 84),
  (14, 85),
  (14, 86),
  (14, 87),
  (15, 88),
  (15, 89),
  (15, 90),
  (15, 91),
  (15, 92),
  (16, 93),
  (16, 94),
  (16, 95),
  (16, 96),
  (16, 97),
  (16, 98),
  (17, 99),
  (17, 100),
  (17, 101);
*/