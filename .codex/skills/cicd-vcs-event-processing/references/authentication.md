# Authentication
GitHub: verify HMAC-SHA256 of **raw** request body using `X-Hub-Signature-256`, reject missing/malformed signatures and use constant-time comparison.
GitLab: current signing-token flow uses `webhook-id`, `webhook-timestamp`, `webhook-signature`, signed `{message_id}.{timestamp}.{body}` with HMAC-SHA256 and base64. Verify timestamp freshness and message ID; define clock-skew and replay policies. Older GitLab installations may use a shared secret header (`X-Gitlab-Token`), which does not provide body integrity; document downgrade risk rather than silently accepting it.
Authenticate before parsing/side effects. Limit body size, reject wrong provider/repository, redact headers and secrets in logs. Webhook authentication does not establish that a PR author is trusted.
