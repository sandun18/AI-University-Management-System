'use client';

import React from 'react';
import Link from 'next/link';
import { usePathname, useRouter } from 'next/navigation';
import {
  LayoutDashboard,
  Users,
  BookOpen,
  CalendarDays,
  Building2,
  GraduationCap,
  Sparkles,
  Settings,
  LogOut,
  ChevronLeft,
  ChevronRight,
  Shield,
  Bot
} from 'lucide-react';
import { useAppDispatch, useAppSelector } from '@/redux/hooks';
import { logout } from '@/redux/slices/authSlice';
import styles from './sidebar.module.css';

interface SidebarProps {
  collapsed: boolean;
  setCollapsed: (val: boolean) => void;
  mobileOpen: boolean;
  setMobileOpen: (val: boolean) => void;
}

interface NavItemConfig {
  label: string;
  href: string;
  icon: React.ComponentType<{ size?: number; className?: string }>;
  badge?: string;
  isAi?: boolean;
}

const mainNavItems: NavItemConfig[] = [
  { label: 'Overview', href: '/', icon: LayoutDashboard },
  { label: 'Students', href: '/students', icon: Users, badge: 'Core' },
  { label: 'Courses', href: '/courses', icon: BookOpen },
  { label: 'Timetable', href: '/timetable', icon: CalendarDays },
  { label: 'Classrooms', href: '/rooms', icon: Building2 },
  { label: 'Faculty', href: '/lecturers', icon: GraduationCap },
];

const secondaryNavItems: NavItemConfig[] = [
  { label: 'AI Academic Advisor', href: '/ai-advisor', icon: Bot, badge: 'AI', isAi: true },
  { label: 'System Settings', href: '/settings', icon: Settings },
];

export default function Sidebar({
  collapsed,
  setCollapsed,
  mobileOpen,
}: SidebarProps) {
  const pathname = usePathname();
  const router = useRouter();
  const dispatch = useAppDispatch();
  const { user } = useAppSelector((state) => state.auth);

  const handleLogout = () => {
    dispatch(logout());
    router.push('/login');
  };

  const username = user?.username || 'Guest User';
  const role = user?.role || 'STUDENT';

  return (
    <aside
      className={`${styles.sidebar} ${collapsed ? styles.sidebarCollapsed : ''} ${
        mobileOpen ? styles.sidebarMobileOpen : ''
      }`}
    >
      {/* Brand Header */}
      <div className={styles.brandHeader}>
        <Link href="/" className={styles.brandLink}>
          <div className={styles.brandLogo}>
            <Sparkles size={20} />
          </div>
          {!collapsed && (
            <div className={styles.brandText}>
              <span className={styles.brandTitle}>AI University</span>
              <span className={styles.brandSubtitle}>Management Portal</span>
            </div>
          )}
        </Link>
        <button
          onClick={() => setCollapsed(!collapsed)}
          className={styles.toggleBtn}
          title={collapsed ? 'Expand sidebar' : 'Collapse sidebar'}
          aria-label="Toggle sidebar"
        >
          {collapsed ? <ChevronRight size={16} /> : <ChevronLeft size={16} />}
        </button>
      </div>

      {/* Navigation */}
      <nav className={styles.nav}>
        {!collapsed && <div className={styles.navSectionLabel}>Academic Core</div>}
        {mainNavItems.map((item) => {
          const Icon = item.icon;
          const isActive = pathname === item.href;
          return (
            <Link
              key={item.href}
              href={item.href}
              className={`${styles.navItem} ${isActive ? styles.navItemActive : ''}`}
              title={collapsed ? item.label : undefined}
            >
              <Icon size={19} className={styles.navIcon} />
              {!collapsed && (
                <>
                  <span className={styles.navLabel}>{item.label}</span>
                  {item.badge && (
                    <span
                      className={`${styles.badge} ${
                        item.isAi ? styles.badgeAi : ''
                      }`}
                    >
                      {item.badge}
                    </span>
                  )}
                </>
              )}
            </Link>
          );
        })}

        {!collapsed && (
          <div className={styles.navSectionLabel} style={{ marginTop: '0.75rem' }}>
            Intelligence & Admin
          </div>
        )}
        {secondaryNavItems.map((item) => {
          const Icon = item.icon;
          const isActive = pathname === item.href;
          return (
            <Link
              key={item.href}
              href={item.href}
              className={`${styles.navItem} ${isActive ? styles.navItemActive : ''}`}
              title={collapsed ? item.label : undefined}
            >
              <Icon size={19} className={styles.navIcon} />
              {!collapsed && (
                <>
                  <span className={styles.navLabel}>{item.label}</span>
                  {item.badge && (
                    <span
                      className={`${styles.badge} ${
                        item.isAi ? styles.badgeAi : ''
                      }`}
                    >
                      {item.badge}
                    </span>
                  )}
                </>
              )}
            </Link>
          );
        })}
      </nav>

      {/* User Footer Card */}
      <div className={styles.footerSection}>
        <div className={styles.userCard}>
          <div className={styles.userAvatar}>
            {username.charAt(0).toUpperCase()}
          </div>
          {!collapsed && (
            <>
              <div className={styles.userInfo}>
                <div className={styles.userName}>{username}</div>
                <span className={styles.userRole}>
                  <Shield size={10} style={{ display: 'inline', marginRight: 3 }} />
                  {role}
                </span>
              </div>
              <button
                onClick={handleLogout}
                className={styles.logoutBtn}
                title="Sign out"
                aria-label="Sign out"
              >
                <LogOut size={16} />
              </button>
            </>
          )}
        </div>
      </div>
    </aside>
  );
}
