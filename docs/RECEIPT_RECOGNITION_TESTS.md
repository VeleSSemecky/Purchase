# Receipt Recognition — Test Log

Unified log for tracking AI receipt recognition quality across different receipts, engines and prompt versions.

---

## How to add a test case

1. Copy a **Test Case** block below.
2. Fill in the raw text from the receipt.
3. Fill in **Expected JSON** (ground truth — what the parser *should* return).
4. Run the app, scan the receipt, and paste the **Actual JSON** the app produced.
5. Mark each item row ✅ correct / ❌ wrong / ⚠️ partial.

---

## Expected JSON Schema

```json
{
  "total": <number>,
  "currency": "<ISO 4217 code>",
  "items": [
    { "name": "<string>", "price": <line total, number>, "qty": <number>, "tax": "<string or empty>" }
  ]
}
```

**Rules:**
- `price` = line total (unit_price × qty), NOT unit price
- `qty` = numeric quantity (default 1)
- `tax` = per-line VAT code/label if printed, otherwise `""`
- Decimal separator: always `.` (e.g. `12.99`)
- SKIP: subtotals, VAT summary rows, discounts, payment rows, headers, store info

---

## Test Cases

---

### TC-001 — Casa 15, Alicante (bar/restaurant, Spanish, EUR)

**Image:** `20260601_202210.jpg`  
**Store:** GREENTOUCH UA SL / Casa 15, Alicante  
**Date on receipt:** 10/05/2026  
**Language:** Spanish  
**Currency:** EUR (€ prefix)  
**Format:** PRODUCTO | PRECIO (unit) | CANT. | TOTAL (line)

**Raw receipt text:**
```
PRODUCTO        PRECIO   CANT.  TOTAL
CocaCola 0.2    €3.00    1      €3.00
New York Sour   €8.00    2      €16.00
Negroni         €8.00    3      €24.00
Gin Tonic       €7.00    3      €21.00
Sin gas 0.5     €2.00    2      €4.00
Long Island     €12.00   3      €36.00
Red Labe        €2.50    1      €2.50
Tabla de quesos €10.00   1      €10.00
Red Labe        €2.50    4      €10.00
Gin Tonic       €7.00    2      €14.00

Parcial         €140.50
Total           €140.50
IVA% 10.00%     €12.77
```

**Expected JSON:**
```json
{
  "total": 140.50,
  "currency": "EUR",
  "items": [
    { "name": "CocaCola 0.2",    "price": 3.00,  "qty": 1, "tax": "" },
    { "name": "New York Sour",   "price": 16.00, "qty": 2, "tax": "" },
    { "name": "Negroni",         "price": 24.00, "qty": 3, "tax": "" },
    { "name": "Gin Tonic",       "price": 21.00, "qty": 3, "tax": "" },
    { "name": "Sin gas 0.5",     "price": 4.00,  "qty": 2, "tax": "" },
    { "name": "Long Island",     "price": 36.00, "qty": 3, "tax": "" },
    { "name": "Red Labe",        "price": 2.50,  "qty": 1, "tax": "" },
    { "name": "Tabla de quesos", "price": 10.00, "qty": 1, "tax": "" },
    { "name": "Red Labe",        "price": 10.00, "qty": 4, "tax": "" },
    { "name": "Gin Tonic",       "price": 14.00, "qty": 2, "tax": "" }
  ]
}
```

**Tricky parts:**
- "Red Labe" appears twice (likely "Red Label" — OCR cut-off, keep as-is)
- "Gin Tonic" appears twice with different qty/price
- Model must use TOTAL column, NOT PRECIO column
- `€` prefix (not suffix)

| Date       | Engine     | Model                                     | Items ✅ | Total ✅ | Notes                       |
|------------|------------|-------------------------------------------|---------|---------|------------------------------|
| 2026-06-21 | Groq       | meta-llama/llama-4-scout-17b-16e-instruct | ?/10    | ?       | Baseline before prompt split |
| 2026-06-22 | Groq       | meta-llama/llama-4-scout-17b-16e-instruct | 10/10   | ✅      | New prompt v2 — items count OK, but prices are unit prices (not line totals) |
| 2026-06-22 | Groq       | meta-llama/llama-4-scout-17b-16e-instruct | 10/10   | ✅      | New prompt v3 — items count ✅, prices = line totals ✅, tax="10%" (minor) |

---

### TC-002 — Foto Panek, Warszawa (photo studio, Polish, PLN)

**Image:** `20260607_031448.jpg`  
**Store:** FOTO PANEK SPÓŁKA JAWNA  
**Date on receipt:** 2026-06-05  
**Language:** Polish  
**Currency:** PLN (suffix, e.g. `50,00 PLN`)  
**Format:** Polish fiscal receipt (paragon fiskalny) — NAME QTY x UNIT_PRICE  LINE_TOTAL + TAX_CODE

**Raw receipt text:**
```
FOTO PANEK SPÓŁKA JAWNA
Stacja Metra C-04 Bemowo lokal 1013
Warszawa 01-381
NIP 6793250774

NIP 6793250774 nr: 13156
PARAGON FISKALNY
Zdjęcia do dokumentów 1 x50,00 50,00A

SPRZEDAZ OPODATK. A 50,00
PTU A 23%           9,35
SUMA PTU            9,35
SUMA PLN           50,00

ROZLICZENIE PŁATNOŚCI
KARTA Karta 50,00 PLN
2026-06-05 14:38
```

**Expected JSON:**
```json
{
  "total": 50.00,
  "currency": "PLN",
  "items": [
    { "name": "Zdjęcia do dokumentów", "price": 50.00, "qty": 1, "tax": "A" }
  ]
}
```

**Tricky parts:**
- Polish decimal separator is `,` — `50,00` must become `50.00`
- Tax code `A` is appended directly to the price: `50,00A` — model must extract it as `tax: "A"`
- VAT section (`PTU A 23% 9,35`, `SUMA PLN`) must be **skipped** entirely
- Payment row (`KARTA Karta 50,00 PLN`) must be **skipped**
- Only 1 product on this receipt

| Date       | Engine | Model                                     | Items ✅ | Total ✅ | Notes   |
|------------|--------|-------------------------------------------|---------|---------|---------|
| 2026-06-22 | Groq   | meta-llama/llama-4-scout-17b-16e-instruct | 1/1     | ✅      | New prompt v3 — perfect |

---

### TC-003 — Kaufland, Warszawa (supermarket, Polish, PLN) ⭐ complex

**Image:** `20260607_032641.jpg`  
**Store:** Kaufland Polska Markety Sp. z o.o. Sp.j.  
**Date on receipt:** 2026-06-05  
**Language:** Polish  
**Currency:** PLN (suffix `PLN`, comma decimal: `14,99`)  
**Format:** Polish fiscal receipt — `NAME [t] QTY x UNIT_PRICE LINE_TOTAL TAX_CODE`

**Raw receipt text:**
```
PARAGON FISKALNY
SzczotkaModelowanie H 1SZT x15,79 15,79A
OpaskaKokardESchm H 1SZT x14,99 14,99A
EwaScGrzebGR061 1szt H 1SZT x6,49 6,49A
SensodyneWhiPasta75mH 1SZT x13,29 13,29A
CillitKamienBrud750 H 1SZT x18,95 18,95A
SokołówMozzarellaKG t 0,232kg x22,89 5,31C
ZPrzechlewaPolędwicaSopocLuz t 0,264kg x16,89 4,46C
SzynkaStaropolskaKg t 0,216kg x24,91 5,38C
Frosta Szpinak 300g t 1SZT x4,69 4,69C
KGoldParmigReggPłatki125g t 1SZT x12,99 12,99C
TwójSmakSerek135g t 1SZT x3,99 3,99C
JoviNapójJogurto350g t 1SZT x2,99 2,99C
Jaja10szt.WolnyWybie t 1SZT x14,49 14,49C
MlekoŁaciate2% 1l t 1SZT x4,29 4,29C
HortexSokPomarańcz1L t 1SZT x5,99 5,99C
CisowWoda1,5 H 1SZT x2,49 2,49A
PrimaveraWoda1,5L H 1SZT x1,19 1,19A
Polskie ziemni 1,5kg t 1SZT x5,99 5,99C
Pomid rzym mini 500gt 1SZT x14,99 14,99C
OPUST Cena z kartą -8,00C
Czosnek 200g t 1SZT x7,99 7,99C
Donut z kolorową posypką 54g t 4SZT x1,99 7,96C
OPUST Trzeci 100% taniej -1,99C
KLCTuńczykWOsoSwł3x80gt 1SZT x9,99 9,99C
CostaOliwaEV1L t 1SZT x49,99 49,99C
BarillaSpaghe t 2SZT x5,99 11,98C
OPUST Cena z kartą -5,99C
KaszaKukurydz450g t 1SZT x2,99 2,99C
BarilaSosRicotta400gi 1SZT x13,99 13,99B
STD.Chleb.graham.450g t 1SZT x3,79 3,79C

OPUSTY ŁĄCZNIE -15,98
SPRZEDAŻ OPODATKOWANA A 73,19
SPRZEDAŻ OPODATKOWANA B 13,99
SPRZEDAŻ OPODATKOWANA C 164,27
PTU A 23% 13,69
PTU B 8%   1,04
PTU C 5%   7,82
SUMA PTU  22,55
SUMA PLN 251,45

OPAKOWANIA ZWROTNE WYDANIA
butelkaPET(1) 3,0 x0,50 1,50
OPAKOWANIA ZWROTNE SUMA 1,50

DO ZAPŁATY 252,95 PLN
```

**Expected JSON** _(app schema — `price` = line total after any inline discount)_:
```json
{
  "total": 252.95,
  "currency": "PLN",
  "items": [
    { "name": "SzczotkaModelowanie",         "price": 15.79, "qty": 1,     "tax": "A" },
    { "name": "OpaskaKokardESchm",           "price": 14.99, "qty": 1,     "tax": "A" },
    { "name": "EwaScGrzebGR061 1szt",        "price": 6.49,  "qty": 1,     "tax": "A" },
    { "name": "SensodyneWhiPasta75m",        "price": 13.29, "qty": 1,     "tax": "A" },
    { "name": "CillitKamienBrud750",         "price": 18.95, "qty": 1,     "tax": "A" },
    { "name": "SokołówMozzarellaKG",         "price": 5.31,  "qty": 0.232, "tax": "C" },
    { "name": "ZPrzechlewaPolędwicaSopocLuz","price": 4.46,  "qty": 0.264, "tax": "C" },
    { "name": "SzynkaStaropolskaKg",         "price": 5.38,  "qty": 0.216, "tax": "C" },
    { "name": "Frosta Szpinak 300g",         "price": 4.69,  "qty": 1,     "tax": "C" },
    { "name": "KGoldParmigReggPłatki125g",   "price": 12.99, "qty": 1,     "tax": "C" },
    { "name": "TwójSmakSerek135g",           "price": 3.99,  "qty": 1,     "tax": "C" },
    { "name": "JoviNapójJogurto350g",        "price": 2.99,  "qty": 1,     "tax": "C" },
    { "name": "Jaja10szt.WolnyWybie",        "price": 14.49, "qty": 1,     "tax": "C" },
    { "name": "MlekoŁaciate2% 1l",           "price": 4.29,  "qty": 1,     "tax": "C" },
    { "name": "HortexSokPomarańcz1L",        "price": 5.99,  "qty": 1,     "tax": "C" },
    { "name": "CisowWoda1,5",                "price": 2.49,  "qty": 1,     "tax": "A" },
    { "name": "PrimaveraWoda1,5L",           "price": 1.19,  "qty": 1,     "tax": "A" },
    { "name": "Polskie ziemni 1,5kg",        "price": 5.99,  "qty": 1,     "tax": "C" },
    { "name": "Pomid rzym mini 500g",        "price": 14.99, "qty": 1,     "tax": "C" },
    { "name": "OPUST Cena z kartą",          "price": -8.00, "qty": 1,     "tax": "C" },
    { "name": "Czosnek 200g",                "price": 7.99,  "qty": 1,     "tax": "C" },
    { "name": "Donut z kolorową posypką 54g","price": 7.96,  "qty": 4,     "tax": "C" },
    { "name": "OPUST Trzeci 100% taniej",    "price": -1.99, "qty": 1,     "tax": "C" },
    { "name": "KLCTuńczykWOsoSwł3x80g",     "price": 9.99,  "qty": 1,     "tax": "C" },
    { "name": "CostaOliwaEV1L",              "price": 49.99, "qty": 1,     "tax": "C" },
    { "name": "BarillaSpaghe",               "price": 11.98, "qty": 2,     "tax": "C" },
    { "name": "OPUST Cena z kartą",          "price": -5.99, "qty": 1,     "tax": "C" },
    { "name": "KaszaKukurydz450g",           "price": 2.99,  "qty": 1,     "tax": "C" },
    { "name": "BarilaSosRicotta400g",        "price": 13.99, "qty": 1,     "tax": "B" },
    { "name": "STD.Chleb.graham.450g",       "price": 3.79,  "qty": 1,     "tax": "C" },
    { "name": "butelkaPET(1)",               "price": 1.50,  "qty": 3,     "tax": "" }
  ]
}
```

**Tricky parts:**
- Polish comma-decimal: `14,99` → `14.99`
- Tax code is the **last character** of the price token: `15,79A` → price=15.79, tax="A"
- Weight items: `0,232kg x22,89 5,31C` → qty=0.232, price=5.31 (line total, NOT unit price 22.89)
- **OPUST** (discount) rows have **negative** prices — must be included, not skipped
- Three tax rates on one receipt: A=23%, B=8%, C=5%
- `butelkaPET` is returnable packaging added **after** SUMA PLN → include as item, contributes to DO ZAPŁATY
- `total` = **DO ZAPŁATY** (252.95), not SUMA PLN (251.45)
- SKIP: all `SPRZEDAŻ OPODATKOWANA`, `PTU`, `SUMA PTU`, `SUMA PLN`, `OPUSTY ŁĄCZNIE`, payment rows, store footer

| Date       | Engine | Model                                     | Items ✅ | Total ✅ | Notes   |
|------------|--------|-------------------------------------------|---------|---------|---------|
| 2026-06-22 | Groq   | meta-llama/llama-4-scout-17b-16e-instruct | ~20/31  | ✅      | New prompt v3 — model ceiling reached; names hallucinated, ~10 items missing. Only vision model available on this Groq account. |

---

<!-- TEMPLATE — copy this block for new test cases

### TC-XXX — <Store name> (<country/city>, <language>, <currency>)

**Image:** `<filename>`
**Store:** 
**Date on receipt:** 
**Language:** 
**Currency:** 
**Format:** 

**Raw receipt text:**
```
<paste here>
```

**Expected JSON:**
```json
{
  "total": ,
  "currency": "",
  "items": [
    { "name": "", "price": , "qty": , "tax": "" }
  ]
}
```

**Tricky parts:**
- 

| Date | Engine | Model | Items ✅ | Total ✅ | Notes |
|------|--------|-------|---------|---------|-------|
|      |        |       |         |         |       |

-->


