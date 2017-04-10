INSERT INTO `Category` (`CategoryID`, `ColorCode`, `Name`, `Priority`) VALUES (1, '#f0f0f0', 'Dishes', 1);
INSERT INTO `Category` (`CategoryID`, `ColorCode`, `Name`, `Priority`) VALUES (2, '#454545', 'Appetizers', 2);
INSERT INTO `Category` (`CategoryID`, `ColorCode`, `Name`, `Priority`) VALUES (3, '#454534', 'Desserts', 3);
INSERT INTO `Category` (`CategoryID`, `ColorCode`, `Name`, `Priority`) VALUES (4, '#233212', 'Beverages', 4);

INSERT INTO `Item` (`ItemID`, `Comment`, `ItemName`, `Portion`, `Price`, `Priority`, `SKUCode`, `TaxCode`, `CategoryID`,`Active`) VALUES (1, 'Comment 01', 'Mongolian Rice', 'Large', 750, 1, '23', 34, 1,1);
INSERT INTO `Item` (`ItemID`, `Comment`, `ItemName`, `Portion`, `Price`, `Priority`, `SKUCode`, `TaxCode`, `CategoryID`,`Active`) VALUES (2, 'Comment 02', 'Mongolian Rice', 'Medium', 600, 1, '34', 12, 1,1);
INSERT INTO `Item` (`ItemID`, `Comment`, `ItemName`, `Portion`, `Price`, `Priority`, `SKUCode`, `TaxCode`, `CategoryID`,`Active`) VALUES (3, 'Comment 03', 'Mixed Rice', 'Medium', 550, 3, '56', 23, 1,1);
INSERT INTO `Item` (`ItemID`, `Comment`, `ItemName`, `Portion`, `Price`, `Priority`, `SKUCode`, `TaxCode`, `CategoryID`,`Active`) VALUES (4, 'Commment 04', 'Cream Soda 300ml', NULL, 80, 4, '34', 12, 4,1);


INSERT INTO `Orders` (`OrderID`, `Amount`, `TableId`, `ReceivedTime`, `Status`, `CustomerName`) VALUES (1, 4500, '2017-02-14 07:08:00', '2', 'In kitchen','Sugeesh');
INSERT INTO `Orders` (`OrderID`, `Amount`, `TableId`, `ReceivedTime`, `Status`, `CustomerName`) VALUES (2, 5500, '2017-02-17 00:00:00', '1', 'Delevered','Buddhiv');


INSERT INTO `OrderDetail` (`OrderDetailID`, `UnitPrice`, `Quantity`, `ItemID`, `OrderID`) VALUES (1, 400, 4, 2, 1);
INSERT INTO `OrderDetail` (`OrderDetailID`, `UnitPrice`, `Quantity`, `ItemID`, `OrderID`) VALUES (2, 1400, 2, 1, 1);


INSERT INTO `Payment` (`PaymentID`, `Amount`, `Date`, `Type`) VALUES (1, 3400, '2017-02-14 00:00:00', 1);
INSERT INTO `Payment` (`PaymentID`, `Amount`, `Date`, `Type`) VALUES (2, 4500, '2017-02-17 00:00:00', 1);

INSERT INTO rms.User (Name, Nic, Password, Telephone, Type, Username) VALUES ('admin', null, 'admin', null, 1, 'admin');
INSERT INTO rms.User (Name, Nic, Password, Telephone, Type, Username) VALUES ('cashier', null, 'cashier', null, 2, 'cashier');
