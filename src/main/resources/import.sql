INSERT INTO `Kitchen` (`Name`) VALUES ('Night Kade');
INSERT INTO `Kitchen` (`Name`) VALUES ('Take Away');
INSERT INTO `Kitchen` (`Name`) VALUES ('In House');
INSERT INTO `Kitchen` (`Name`) VALUES ('Juice Bar');


INSERT INTO `Category` (`CategoryID`, `ColorCode`, `Name`, `Priority`) VALUES (1, '#f0f0f0', 'Dishes', 1);
INSERT INTO `Category` (`CategoryID`, `ColorCode`, `Name`, `Priority`) VALUES (2, '#454545', 'Appetizers', 2);
INSERT INTO `Category` (`CategoryID`, `ColorCode`, `Name`, `Priority`) VALUES (3, '#454534', 'Desserts', 3);
INSERT INTO `Category` (`CategoryID`, `ColorCode`, `Name`, `Priority`) VALUES (4, '#233212', 'Beverages', 4);

INSERT INTO `Item` (`ItemID`, `Comment`, `ItemName`, `Portion`, `Price`, `Priority`, `SKUCode`, `TaxCode`, `CategoryID`,`KitchenID`,`Active`,`isTakeAway`) VALUES (1, 'Comment 01', 'Mongolian Rice', 'Large', 750, 1, '23', 34, 1,1,1,true);
INSERT INTO `Item` (`ItemID`, `Comment`, `ItemName`, `Portion`, `Price`, `Priority`, `SKUCode`, `TaxCode`, `CategoryID`,`KitchenID`,`Active`,`isTakeAway`) VALUES (2, 'Comment 02', 'Mongolian Rice', 'Medium', 600, 1, '34', 12, 1,2,1,true);
INSERT INTO `Item` (`ItemID`, `Comment`, `ItemName`, `Portion`, `Price`, `Priority`, `SKUCode`, `TaxCode`, `CategoryID`,`KitchenID`,`Active`,`isTakeAway`) VALUES (3, 'Comment 03', 'Mixed Rice', 'Medium', 550, 3, '56', 23, 1,3,1,false);
INSERT INTO `Item` (`ItemID`, `Comment`, `ItemName`, `Portion`, `Price`, `Priority`, `SKUCode`, `TaxCode`, `CategoryID`,`KitchenID`,`Active`,`isTakeAway`) VALUES (4, 'Commment 04', 'Cream Soda 300ml', 'Medium', 80, 4, '34', 12, 4,4,1,true);

INSERT INTO rms.User (Name, Nic, Password, Telephone, Type, Username) VALUES ('admin', null, 'admin', null, 1, 'admin');
INSERT INTO rms.User (Name, Nic, Password, Telephone, Type, Username) VALUES ('cashier', null, 'cashier', null, 2, 'cashier');
INSERT INTO rms.User (Name, Nic, Password, Telephone, Type, Username) VALUES ('waiter', null, 'waiter', null, 3, 'waiter');
INSERT INTO rms.User (Name, Nic, Password, Telephone, Type, Username) VALUES ('kitchen', null, 'kitchen', null, 4, 'kitchen');

