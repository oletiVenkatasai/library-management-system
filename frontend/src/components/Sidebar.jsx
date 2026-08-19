import { NavLink } from 'react-router-dom';

/**
 * Sidebar Component - Side navigation with links to all pages.
 * Uses NavLink for active state highlighting.
 */
function Sidebar() {
  const links = [
    { to: '/dashboard', label: 'Dashboard', icon: 'bi-speedometer2' },
    { to: '/books', label: 'Books', icon: 'bi-journal-text' },
    { to: '/authors', label: 'Authors', icon: 'bi-person-lines-fill' },
    { to: '/members', label: 'Members', icon: 'bi-people-fill' },
    { to: '/borrow/issue', label: 'Issue Book', icon: 'bi-box-arrow-right' },
    { to: '/borrowings', label: 'Borrowings', icon: 'bi-clock-history' },
  ];

  return (
    <div className="sidebar bg-light border-end p-3" style={{ width: '220px', minHeight: 'calc(100vh - 56px)' }}>
      <ul className="nav flex-column">
        {links.map((link) => (
          <li className="nav-item mb-1" key={link.to}>
            <NavLink
              to={link.to}
              className={({ isActive }) =>
                `nav-link rounded ${isActive ? 'active bg-primary text-white' : 'text-dark'}`
              }
            >
              <i className={`bi ${link.icon} me-2`}></i>
              {link.label}
            </NavLink>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default Sidebar;
