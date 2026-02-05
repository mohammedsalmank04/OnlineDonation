

SELECT d.donation_id,
d.description,
d.donation_amount,
u.username,
u.first_name,
u.last_name,
u.phone_number,
o.organization_name,
dc.category_name
FROM donation d 
JOIN
organization o ON d.organization_organization_id = o.organization_id
JOIN
users u on d.user_id = u.user_id
JOIN
donation_category dc on o.category_id = dc.donation_category_id
WHERE d.user_id = 3;
