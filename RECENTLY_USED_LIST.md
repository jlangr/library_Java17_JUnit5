Scenarios:

x Most recent one should be at the top of the list.
Rolls over the files when exceeding max size.
x many: Accepts up to the limit of files
x 1: Accepts a single file
x 0: Ask for a list when no files added
Filter out unsupported files
Truncates long file names

Stock portfolio I1

Has 0 unique symbols when no purchases made

Has 1 unique symbol after a single purchase
    purchase(NOK, 10)
    assert uniqueSymbols() == 1

Has 1 unique symbol after multiple purchases of the same symbol
    purchase(NOK, 10)
    purchase(NOK, 5)
    assert uniqueSymbols() == 1

Has multiple unique symbols after purchases of different symbols
    purchase(NOK, 10)
    purchase(AAPL, 15)
    assert uniqueSymbols() == 2

Has multiple unique symbols with purchases of similar symbols
    purchase(NOKI, 10)
    purchase(NOK, 15)
    assert uniqueSymbols() == 2

Don't forget these cases:
    Can a purchase fail?