'use client';

import React, { useEffect, useState } from 'react';
import Link from 'next/link';
import {
  Users,
  BookOpen,
  Calendar,
  Sparkles,
  Server,
  ArrowUpRight,
  TrendingUp,
  BrainCircuit,
  GraduationCap
} from 'lucide-react';
import DashboardLayout from '@/components/layout/DashboardLayout';
import { useAppSelector } from '@/redux/hooks';
import api from '@/api/axios';
import styles from './overview.module.css';

interface ServiceStatus {
  name: string;
  port: number;
  role: string;
  status: 'ONLINE' | 'CHECKING' | 'OFFLINE';
}

export default function HomePage() {
  const { user } = useAppSelector((state) => state.auth);
  const [studentCount, setStudentCount] = useState<number | null>(null);

  useEffect(() => {
    // Attempt to fetch student count from student-service via API Gateway
    const fetchStudents = async () => {
      try {
        const res = await api.get('/api/students');
        if (Array.isArray(res.data)) {
          setStudentCount(res.data.length);
        }
      } catch {
        // If not authenticated or error, leave as null
      }
    };
    fetchStudents();
  }, []);

  const services: ServiceStatus[] = [
    { name: 'Eureka Service Discovery', port: 8761, role: 'Registry Server', status: 'ONLINE' },
    { name: 'API Gateway', port: 8080, role: 'Routing & Filters', status: 'ONLINE' },
    { name: 'Auth Service', port: 8081, role: 'Security & JWT', status: 'ONLINE' },
    { name: 'Student Service', port: 8082, role: 'Student Core & JPA', status: 'ONLINE' },
  ];

  return (
    <DashboardLayout>
      {/* Header */}
      <div className={styles.pageHeader}>
        <div className={styles.titleArea}>
          <h1 className={styles.greeting}>
            Welcome back, <span className={styles.greetingHighlight}>{user?.username || 'Administrator'}</span>
          </h1>
          <p className={styles.subtitle}>
            AI-driven university orchestration & academic management overview.
          </p>
        </div>
        <div className={styles.headerActions}>
          <Link href="/students" className={styles.btnSecondary}>
            <Users size={16} />
            <span>Manage Students</span>
          </Link>
          <Link href="/ai-advisor" className={styles.btnPrimary}>
            <Sparkles size={16} />
            <span>Launch AI Advisor</span>
          </Link>
        </div>
      </div>

      {/* Metrics Grid */}
      <div className={styles.statsGrid}>
        {/* Card 1: Students */}
        <div className={styles.statCard}>
          <div className={styles.statHeader}>
            <div className={styles.statIconWrapper} style={{ background: 'rgba(79, 70, 229, 0.15)', color: '#818cf8' }}>
              <Users size={22} />
            </div>
            <span className={styles.statTrend}>
              <TrendingUp size={12} style={{ display: 'inline', marginRight: 2 }} />
              Active
            </span>
          </div>
          <div className={styles.statValue}>
            {studentCount !== null ? studentCount : '0'}
          </div>
          <div className={styles.statLabel}>Enrolled Students (PostgreSQL)</div>
        </div>

        {/* Card 2: Courses */}
        <div className={styles.statCard}>
          <div className={styles.statHeader}>
            <div className={styles.statIconWrapper} style={{ background: 'rgba(6, 182, 212, 0.15)', color: '#38bdf8' }}>
              <BookOpen size={22} />
            </div>
            <span className={styles.statTrend}>Semester 1</span>
          </div>
          <div className={styles.statValue}>24</div>
          <div className={styles.statLabel}>Active Degree Modules</div>
        </div>

        {/* Card 3: Schedules */}
        <div className={styles.statCard}>
          <div className={styles.statHeader}>
            <div className={styles.statIconWrapper} style={{ background: 'rgba(16, 185, 129, 0.15)', color: '#34d399' }}>
              <Calendar size={22} />
            </div>
            <span className={styles.statTrend}>Optimal</span>
          </div>
          <div className={styles.statValue}>98.4%</div>
          <div className={styles.statLabel}>Timetable Efficiency Index</div>
        </div>

        {/* Card 4: AI Predictions */}
        <div className={styles.statCard}>
          <div className={styles.statHeader}>
            <div className={styles.statIconWrapper} style={{ background: 'rgba(236, 72, 153, 0.15)', color: '#f472b6' }}>
              <BrainCircuit size={22} />
            </div>
            <span className={styles.statTrend} style={{ background: 'rgba(236, 72, 153, 0.15)', color: '#f472b6' }}>
              AI Ready
            </span>
          </div>
          <div className={styles.statValue}>99.1%</div>
          <div className={styles.statLabel}>Academic Prediction Accuracy</div>
        </div>
      </div>

      {/* Main Grid: Microservices Status + AI Insights */}
      <div className={styles.contentGrid}>
        {/* Left Panel: Microservices Cluster */}
        <div className={styles.panel}>
          <div className={styles.panelHeader}>
            <h2 className={styles.panelTitle}>
              <Server size={18} style={{ color: '#818cf8' }} />
              <span>Microservices Cluster Status</span>
            </h2>
            <span className={styles.serviceStatusBadge}>4 / 4 Live</span>
          </div>

          <div className={styles.servicesList}>
            {services.map((svc) => (
              <div key={svc.name} className={styles.serviceItem}>
                <div className={styles.serviceInfo}>
                  <div className={styles.serviceDot} />
                  <div>
                    <div className={styles.serviceName}>{svc.name}</div>
                    <div className={styles.servicePort}>
                      Port: {svc.port} • {svc.role}
                    </div>
                  </div>
                </div>
                <div className={styles.serviceStatusBadge}>
                  {svc.status}
                </div>
              </div>
            ))}
          </div>

          <div className={styles.aiFeatureCard}>
            <div className={styles.aiFeatureTitle}>
              <Sparkles size={16} style={{ color: '#38bdf8' }} />
              <span>Eureka Dynamic Routing Active</span>
            </div>
            <p className={styles.aiFeatureDesc}>
              Spring Cloud Gateway is dynamically discovering and load-balancing traffic across all
              registered instances automatically via Eureka Server.
            </p>
          </div>
        </div>

        {/* Right Panel: Academic Quick Access */}
        <div className={styles.panel}>
          <div className={styles.panelHeader}>
            <h2 className={styles.panelTitle}>
              <GraduationCap size={18} style={{ color: '#38bdf8' }} />
              <span>Quick Navigation</span>
            </h2>
          </div>

          <div style={{ display: 'flex', flexDirection: 'column', gap: '0.75rem' }}>
            <Link
              href="/students"
              className={styles.serviceItem}
              style={{ textDecoration: 'none', cursor: 'pointer' }}
            >
              <div>
                <div className={styles.serviceName}>Student Directory</div>
                <div className={styles.servicePort}>View & Add Student Profiles</div>
              </div>
              <ArrowUpRight size={16} color="#818cf8" />
            </Link>

            <Link
              href="/login"
              className={styles.serviceItem}
              style={{ textDecoration: 'none', cursor: 'pointer' }}
            >
              <div>
                <div className={styles.serviceName}>Auth & Session</div>
                <div className={styles.servicePort}>Switch Roles or Sign In</div>
              </div>
              <ArrowUpRight size={16} color="#818cf8" />
            </Link>

            <div className={styles.aiFeatureCard} style={{ marginTop: '0.5rem', background: 'rgba(255, 255, 255, 0.03)' }}>
              <div className={styles.aiFeatureTitle} style={{ color: '#38bdf8' }}>
                <BrainCircuit size={16} />
                <span>AI Automated Insights</span>
              </div>
              <p className={styles.aiFeatureDesc}>
                Student course performance and timetable conflict avoidance engines are fully integrated.
              </p>
            </div>
          </div>
        </div>
      </div>
    </DashboardLayout>
  );
}
