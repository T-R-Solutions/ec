# Instants, Civil Dates, Time Zones and DST

**Never collapse these three types:** an instant (`Z`/offset, point on timeline), a civil date (calendar-only `YYYY-MM-DD`) and elapsed duration (numeric difference in monotonic or declared units). A scheduled local time is another distinct concept and must be paired with a zone and DST disambiguation policy.

**Formatting an instant:** verify input offset/UTC, use the user's explicitly selected IANA zone or approved product default, and format with `Intl.DateTimeFormat(locale,{timeZone,...})`. Show a zone label/offset when an operation depends on knowing the zone. `Date.now()` and browser locale do not prove the desired time zone. When tzdb changes, recalculate display from the same instant without rewriting past evidence.

**Civil date-only:** never interpret a bare date as “midnight in whatever zone the browser has today” and then shift its day across regions; treat date-only as a calendar field and use a UTC formatting representation only when no semantic conversion occurs. Validity and calendar system should be checked.

**DST overlaps/gaps:** e.g. a local 01:30 can occur twice and a 02:30 might never occur. `Intl.DateTimeFormat` formats instants but does **not** provide a universal API for resolving arbitrary local-wall-time input. Require an explicit policy (reject, earlier/later or prompt) and tested temporal conversion implementation. Prefer an approved Temporal implementation if available; do not make a browser support claim without running it.

**Test matrix:** UTC near 23:30 -> prior/next date in selected zones; leap day vs invalid February date; DST spring/fall using a zone and year verified against actual tzdb; malformed zone; invalid timestamp with no offset; date-only remains unchanged across zone choices. Record installed ICU/tzdb for reproducibility.

Sources: https://www.iana.org/time-zones ; https://www.unicode.org/reports/tr35/tr35-dates.html ; https://tc39.es/ecma402/2026/ .
