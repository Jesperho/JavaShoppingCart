CREATE DATABASE IF NOT EXISTS shopping_cart_localization
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE shopping_cart_localization;

CREATE TABLE IF NOT EXISTS cart_records (
    id INT AUTO_INCREMENT PRIMARY KEY,
    total_items INT NOT NULL,
    total_cost DOUBLE NOT NULL,
    language VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS cart_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cart_record_id INT,
    item_number INT NOT NULL,
    price DOUBLE NOT NULL,
    quantity INT NOT NULL,
    subtotal DOUBLE NOT NULL,
    FOREIGN KEY (cart_record_id) REFERENCES cart_records(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS localization_strings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    `key` VARCHAR(100) NOT NULL,
    value VARCHAR(255) NOT NULL,
    language VARCHAR(10) NOT NULL
);

-- English (en_US)
INSERT INTO localization_strings (`key`, value, language) VALUES
('prompt.num.items',   'Enter the number of items to purchase:', 'en_US'),
('prompt.item.name',   'Enter the name for item',                'en_US'),
('prompt.item.price',  'Enter the price for item',               'en_US'),
('prompt.item.quantity','Enter the quantity for item',           'en_US'),
('label.cart.summary', 'Shopping Cart Summary',                  'en_US'),
('label.total.cost',   'Total cost:',                            'en_US'),
('button.set.items',   'Set Items',                              'en_US'),
('button.calculate',   'Calculate',                              'en_US');

-- Finnish (fi_FI)
INSERT INTO localization_strings (`key`, value, language) VALUES
('prompt.num.items',   'Syötä ostettavien tuotteiden määrä:', 'fi_FI'),
('prompt.item.name',   'Syötä tuotteen nimi',                 'fi_FI'),
('prompt.item.price',  'Syötä tuotteen hinta',                'fi_FI'),
('prompt.item.quantity','Syötä tuotteen määrä',               'fi_FI'),
('label.cart.summary', 'Ostoskorin yhteenveto',               'fi_FI'),
('label.total.cost',   'Kokonaishinta:',                      'fi_FI'),
('button.set.items',   'Aseta',                               'fi_FI'),
('button.calculate',   'Laske',                               'fi_FI');

-- Swedish (sv_SE)
INSERT INTO localization_strings (`key`, value, language) VALUES
('prompt.num.items',   'Ange antal artiklar att köpa:', 'sv_SE'),
('prompt.item.name',   'Ange namn på artikel',          'sv_SE'),
('prompt.item.price',  'Ange pris för artikel',         'sv_SE'),
('prompt.item.quantity','Ange antal för artikel',        'sv_SE'),
('label.cart.summary', 'Varukorg sammanfattning',        'sv_SE'),
('label.total.cost',   'Total kostnad:',                 'sv_SE'),
('button.set.items',   'Ange artiklar',                  'sv_SE'),
('button.calculate',   'Beräkna',                        'sv_SE');

-- Japanese (ja_JP)
INSERT INTO localization_strings (`key`, value, language) VALUES
('prompt.num.items',   '購入する商品数を入力してください:', 'ja_JP'),
('prompt.item.name',   '商品名を入力してください',          'ja_JP'),
('prompt.item.price',  '商品の価格を入力してください',      'ja_JP'),
('prompt.item.quantity','商品の数量を入力してください',     'ja_JP'),
('label.cart.summary', 'ショッピングカートの概要',          'ja_JP'),
('label.total.cost',   '合計費用:',                        'ja_JP'),
('button.set.items',   '商品を設定',                       'ja_JP'),
('button.calculate',   '計算する',                         'ja_JP');

-- Arabic (ar_AR)
INSERT INTO localization_strings (`key`, value, language) VALUES
('prompt.num.items',   'أدخل عدد العناصر:',    'ar_AR'),
('prompt.item.name',   'أدخل اسم العنصر',      'ar_AR'),
('prompt.item.price',  'أدخل سعر العنصر',      'ar_AR'),
('prompt.item.quantity','أدخل كمية العنصر',    'ar_AR'),
('label.cart.summary', 'ملخص سلة التسوق',      'ar_AR'),
('label.total.cost',   'التكلفة الإجمالية:',   'ar_AR'),
('button.set.items',   'تعيين',                 'ar_AR'),
('button.calculate',   'احسب',                  'ar_AR');