# Right-to-Left, Mixed-Script IDs and Input

The Unicode bidirectional algorithm lays out characters; the application must still provide correct HTML base direction. Set `<html lang="ar" dir="rtl">` for an Arabic UI, annotate nested foreign-language phrases (`lang`, `dir`) and use `<bdi>`/`dir="auto"` for user-generated unknown-direction strings. For technical immutable tokens (commit hashes, paths, agent IDs), isolate display direction without altering underlying bytes or meaning.

**UI tests:** mixed Arabic text and Latin job ID; digits and punctuation; Hebrew and English within the same row; right-to-left navigation and breadcrumb order; arrow icons that convey back/forward versus icons that must **not** mirror (brand/logos, media content, charts, source-code markers); keyboard and caret traversal; input method editors; multiline inputs with unknown paragraph direction.

**CSS:** prefer `margin-inline-start`, `padding-inline-end`, logical layout and container-based adaptation. Do not rely on `direction:rtl` CSS alone for structural markup; W3C guidance prefers the HTML `dir` attribute. Preserve editable YAML, hashes, code samples and file paths as logical sequences.

**Negative:** a locale switch cannot transform an identifier, reverse a filename, change a commit hash or grant capabilities. Direction auto-detection is not a security boundary; bidi control characters in untrusted content need consideration in logs and audits. Use actual browser inspection and native review to certify visual understanding.

Sources: https://www.w3.org/International/questions/qa-html-dir ; https://www.w3.org/International/articles/inline-bidi-markup/index ; https://www.unicode.org/reports/tr9/ .
