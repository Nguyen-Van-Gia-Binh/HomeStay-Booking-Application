# Flow Chart - Hướng Dẫn

## 1. Luồng Chính (Main Flow)

```
┌─────────────┐
│   START     │
└──────┬──────┘
       │
       ▼
┌─────────────────┐
│  Load Data      │
│  (Homestays,    │
│   Tours)        │
└──────┬──────────┘
       │
       ▼
  ┌────────────┐
  │ File exist?│
  └─────┬──────┘
        │
   ┌────┴────┐
   │         │
  YES        NO
   │         │
   ▼         ▼
┌──────┐  ┌─────────────┐
│      │  │ Show Error  │
│      │  │ & EXIT      │
│      │  └─────────────┘
│      │
│      │
│  ▼───────────────────┐
│  ┌─────────────────┐ │
│  │  Display Menu   │◄┘
│  └──────┬──────────┘
│         │
│    ┌────▼────┐
│    │ Choice? │
│    └────┬────┘
│         │
│  ┌──────┼──────────────────────────┐
│  │      │      │      │           │
│  1      2      3     ...         10
│  │      │      │                  │
│  ▼      ▼      ▼                  ▼
│ Add   Update  List              Quit
│ Tour  Tour    Tours             ─┬─
│  │      │      │                 │
│  └──────┴──────┴────────────────►│
│                                  │
│              ┌───────────────────┘
│              │
│         ┌────▼────┐
│         │ Changes?│
│         └────┬────┘
│              │
│         ┌────┴────┐
│        YES        NO
│         │         │
│         ▼         │
│   ┌──────────┐    │
│   │ Save?Y/N │    │
│   └────┬─────┘    │
│        │          │
│    ┌───┴───┐      │
│   YES      NO     │
│    │       │      │
│    ▼       ▼      │
│  Save    Skip     │
│  Files   Save     │
│    │       │      │
│    └───┬───┘      │
│        │          │
│        ▼          ▼
│    ┌─────────────────┐
└────│      END        │
     └─────────────────┘
```

---

## 2. Add New Tour Flow

```
┌─────────────┐
│ Add Tour    │
└──────┬──────┘
       │
       ▼
┌──────────────┐
│ Input tourID │
└──────┬───────┘
       │
       ▼
  ┌─────────────┐
  │ ID exists?  │
  └──────┬──────┘
         │
    ┌────┴────┐
   YES        NO
    │         │
    ▼         ▼
┌────────┐  ┌──────────────┐
│ Error! │  │ Input other  │
│Return  │  │ fields...    │
└────────┘  └──────┬───────┘
                   │
              ┌────▼────┐
              │Validate │
              │  all?   │
              └────┬────┘
                   │
              ┌────┴────┐
             YES       NO
              │         │
              ▼         ▼
         ┌─────────┐ ┌────────┐
         │Add to   │ │Show    │
         │List     │ │Error   │
         └────┬────┘ └────────┘
              │
              ▼
         ┌─────────┐
         │Success! │
         └─────────┘
```

---

## 3. Add Booking Flow

```
┌───────────────┐
│ Add Booking   │
└───────┬───────┘
        │
        ▼
┌───────────────┐
│Input bookingID│
└───────┬───────┘
        │
        ▼
  ┌──────────────────┐
  │ Format B00000?   │
  │ & Unique?        │
  └───────┬──────────┘
          │
     ┌────┴────┐
    YES       NO─────► Error
     │
     ▼
┌───────────────┐
│ Input fullName│
└───────┬───────┘
        │
        ▼
  ┌─────────────┐
  │ Not empty?  │
  └──────┬──────┘
         │
    ┌────┴────┐
   YES       NO─────► Error
    │
    ▼
┌───────────────┐
│ Input tourID  │
└───────┬───────┘
        │
        ▼
  ┌─────────────┐
  │Tour exists? │
  └──────┬──────┘
         │
    ┌────┴────┐
   YES       NO─────► Error
    │
    ▼
┌────────────────────┐
│ Input booking_date │
└───────┬────────────┘
        │
        ▼
  ┌─────────────────────┐
  │ < departure_date?   │
  └──────────┬──────────┘
             │
        ┌────┴────┐
       YES       NO─────► Error
        │
        ▼
┌───────────────┐
│ Input phone   │
└───────┬───────┘
        │
        ▼
  ┌─────────────┐
  │ 10 digits?  │
  └──────┬──────┘
         │
    ┌────┴────┐
   YES       NO─────► Error
    │
    ▼
┌────────────────┐
│ Add to List    │
│ Update Tour's  │
│ booking = TRUE │
└───────┬────────┘
        │
        ▼
┌───────────────┐
│   Success!    │
└───────────────┘
```

---

## 4. Search Booking by Name Flow

```
┌─────────────────────┐
│ Search by Name      │
└──────────┬──────────┘
           │
           ▼
┌──────────────────────┐
│ Input name/partial   │
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│ Search in list       │
│ (case-insensitive,   │
│  contains match)     │
└──────────┬───────────┘
           │
           ▼
     ┌─────────────┐
     │ Found any?  │
     └──────┬──────┘
            │
       ┌────┴────┐
      YES       NO
       │         │
       ▼         ▼
┌──────────┐ ┌──────────────┐
│ Display  │ │ No bookings  │
│ Table    │ │ found!       │
└──────────┘ └──────────────┘
```

---

## Công Cụ Vẽ Flow Chart

1. **Draw.io** (diagrams.net) - Miễn phí
2. **Lucidchart** - Professional
3. **Microsoft Visio** - Enterprise
4. **Miro** - Collaborative
5. **Creately** - Online tool

---

## Checklist Flow Chart Cần Vẽ

- [X] Main program flow
- [X] Add Tour flow
- [ ] Update Tour flow
- [ ] Add Booking flow
- [ ] Remove Booking flow
- [ ] Search Booking flow
- [ ] Statistics flow
- [ ] Quit & Save flow
