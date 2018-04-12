program BitcoinConversion;
var dollars, yen, bitcoins : integer;



begin


  dollars := 20000;
  yen := 1;
  
  
  while yen <600 do
    begin
      write(yen);
      yen := yen + 100;
    end;

	

  write(dollars);
  write(yen);


end
.
