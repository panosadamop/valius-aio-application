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

describe('InternalReports e2e test', () => {
  const internalReportsPageUrl = '/internal-reports';
  const internalReportsPageUrlPattern = new RegExp('/internal-reports(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const internalReportsSample = {};

  let internalReports;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/internal-reports+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/internal-reports').as('postEntityRequest');
    cy.intercept('DELETE', '/api/internal-reports/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (internalReports) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/internal-reports/${internalReports.id}`,
      }).then(() => {
        internalReports = undefined;
      });
    }
  });

  it('InternalReports menu should load InternalReports page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('internal-reports');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('InternalReports').should('exist');
    cy.url().should('match', internalReportsPageUrlPattern);
  });

  describe('InternalReports page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(internalReportsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create InternalReports page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/internal-reports/new$'));
        cy.getEntityCreateUpdateHeading('InternalReports');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', internalReportsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/internal-reports',
          body: internalReportsSample,
        }).then(({ body }) => {
          internalReports = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/internal-reports+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/internal-reports?page=0&size=20>; rel="last",<http://localhost/api/internal-reports?page=0&size=20>; rel="first"',
              },
              body: [internalReports],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(internalReportsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details InternalReports page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('internalReports');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', internalReportsPageUrlPattern);
      });

      it('edit button click should load edit InternalReports page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('InternalReports');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', internalReportsPageUrlPattern);
      });

      it('edit button click should load edit InternalReports page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('InternalReports');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', internalReportsPageUrlPattern);
      });

      it('last delete button click should delete instance of InternalReports', () => {
        cy.intercept('GET', '/api/internal-reports/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('internalReports').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', internalReportsPageUrlPattern);

        internalReports = undefined;
      });
    });
  });

  describe('new InternalReports page', () => {
    beforeEach(() => {
      cy.visit(`${internalReportsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('InternalReports');
    });

    it('should create an instance of InternalReports', () => {
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        internalReports = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', internalReportsPageUrlPattern);
    });
  });
});
