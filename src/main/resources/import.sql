INSERT INTO `Kitchen` (`Name`) VALUES ('Kitchen1');
INSERT INTO `Kitchen` (`Name`) VALUES ('Kitchen2');
INSERT INTO `Kitchen` (`Name`) VALUES ('Kitchen3');
INSERT INTO `Kitchen` (`Name`) VALUES ('Kitchen4');
INSERT INTO `Kitchen` (`Name`) VALUES ('Kitchen5');

INSERT INTO `Category` (`CategoryID`, `ColorCode`, `Name`, `Priority`) VALUES (1, '#f0f0f0', 'Dishes', 1);
INSERT INTO `Category` (`CategoryID`, `ColorCode`, `Name`, `Priority`) VALUES (2, '#454545', 'Appetizers', 2);
INSERT INTO `Category` (`CategoryID`, `ColorCode`, `Name`, `Priority`) VALUES (3, '#454534', 'Desserts', 3);
INSERT INTO `Category` (`CategoryID`, `ColorCode`, `Name`, `Priority`) VALUES (4, '#233212', 'Beverages', 4);

INSERT INTO `Item` (`ItemID`, `Comment`, `ItemName`, `Portion`, `Price`, `Priority`, `SKUCode`, `TaxCode`, `CategoryID`,`KitchenID`,`Active`) VALUES (1, 'Comment 01', 'Mongolian Rice', 'Large', 750, 1, '23', 34, 1,1,1);
INSERT INTO `Item` (`ItemID`, `Comment`, `ItemName`, `Portion`, `Price`, `Priority`, `SKUCode`, `TaxCode`, `CategoryID`,`KitchenID`,`Active`) VALUES (2, 'Comment 02', 'Mongolian Rice', 'Medium', 600, 1, '34', 12, 1,2,1);
INSERT INTO `Item` (`ItemID`, `Comment`, `ItemName`, `Portion`, `Price`, `Priority`, `SKUCode`, `TaxCode`, `CategoryID`,`KitchenID`,`Active`) VALUES (3, 'Comment 03', 'Mixed Rice', 'Medium', 550, 3, '56', 23, 1,3,1);
INSERT INTO `Item` (`ItemID`, `Comment`, `ItemName`, `Portion`, `Price`, `Priority`, `SKUCode`, `TaxCode`, `CategoryID`,`KitchenID`,`Active`) VALUES (4, 'Commment 04', 'Cream Soda 300ml', 'Medium', 80, 4, '34', 12, 4,4,1);

INSERT INTO rms.User (Name, Nic, Password, Telephone, Type, Username) VALUES ('admin', null, 'admin', null, 1, 'admin');
INSERT INTO rms.User (Name, Nic, Password, Telephone, Type, Username) VALUES ('cashier', null, 'cashier', null, 2, 'cashier');
INSERT INTO rms.User (Name, Nic, Password, Telephone, Type, Username) VALUES ('kitchen', null, 'kitchen', null, 3, 'kitchen');

