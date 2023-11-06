import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('ExternalReports e2e test', () => {
  const externalReportsPageUrl = '/external-reports';
  const externalReportsPageUrlPattern = new RegExp('/external-reports(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const externalReportsSample = { reportUrl: 'York Tuna' };

  let externalReports;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/external-reports+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/external-reports').as('postEntityRequest');
    cy.intercept('DELETE', '/api/external-reports/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (externalReports) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/external-reports/${externalReports.id}`,
      }).then(() => {
        externalReports = undefined;
      });
    }
  });

  it('ExternalReports menu should load ExternalReports page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('external-reports');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ExternalReports').should('exist');
    cy.url().should('match', externalReportsPageUrlPattern);
  });

  describe('ExternalReports page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(externalReportsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ExternalReports page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/external-reports/new$'));
        cy.getEntityCreateUpdateHeading('ExternalReports');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', externalReportsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/external-reports',
          body: externalReportsSample,
        }).then(({ body }) => {
          externalReports = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/external-reports+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/external-reports?page=0&size=20>; rel="last",<http://localhost/api/external-reports?page=0&size=20>; rel="first"',
              },
              body: [externalReports],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(externalReportsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ExternalReports page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('externalReports');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', externalReportsPageUrlPattern);
      });

      it('edit button click should load edit ExternalReports page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ExternalReports');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', externalReportsPageUrlPattern);
      });

      it('edit button click should load edit ExternalReports page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ExternalReports');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', externalReportsPageUrlPattern);
      });

      it('last delete button click should delete instance of ExternalReports', () => {
        cy.intercept('GET', '/api/external-reports/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('externalReports').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', externalReportsPageUrlPattern);

        externalReports = undefined;
      });
    });
  });

  describe('new ExternalReports page', () => {
    beforeEach(() => {
      cy.visit(`${externalReportsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ExternalReports');
    });

    it('should create an instance of ExternalReports', () => {
      cy.get(`[data-cy="reportUrl"]`).type('Causeway methodologies').should('have.value', 'Causeway methodologies');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        externalReports = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', externalReportsPageUrlPattern);
    });
  });
});
