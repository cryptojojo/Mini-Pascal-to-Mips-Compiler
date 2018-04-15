program BitcoinConversion;

var dollars, yen, bitcoins : integer;

begin

  dollars := 10000;
  yen := dollars*107;
  bitcoins := dollars / 8000;

  write(dollars);
  write(yen);
  write(bitcoins);

end
.
